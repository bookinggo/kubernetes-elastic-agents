/*
 * Copyright 2017 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cd.go.contrib.elasticagent.utils;

import cd.go.contrib.elasticagent.executors.GetViewRequestExecutor;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.TimeZone;

import static cd.go.contrib.elasticagent.Constants.KUBERNETES_POD_CREATION_TIME_FORMAT;

public class Util {

    public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KUBERNETES_POD_CREATION_TIME_FORMAT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static String readResource(String resourceFile) {
        try (InputStreamReader reader = new InputStreamReader(GetViewRequestExecutor.class.getResourceAsStream(resourceFile), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new RuntimeException("Could not find resource " + resourceFile, e);
        }
    }

    public static byte[] readResourceBytes(String resourceFile) {
        try (InputStream in = GetViewRequestExecutor.class.getResourceAsStream(resourceFile)) {
            return ByteStreams.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not find resource " + resourceFile, e);
        }
    }

    public static String pluginId() {
        String s = readResource("/plugin.properties");
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(s));
            return (String) properties.get("pluginId");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fullVersion() {
        String s = readResource("/version.properties");
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(s));
            return properties.getProperty("fullVersion");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readableSize(Long memory) {
        Long size = memory * 1024;
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String envOrSystemValueOrDefault(String key, String defaultValue){
        String environmentValue = System.getenv(key);
        if(StringUtils.isNotBlank(environmentValue)){
            return environmentValue;
        }

        return System.getProperty(key,defaultValue);
    }
}
