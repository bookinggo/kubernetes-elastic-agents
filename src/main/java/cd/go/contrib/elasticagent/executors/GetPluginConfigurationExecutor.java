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

package cd.go.contrib.elasticagent.executors;

import cd.go.contrib.elasticagent.RequestExecutor;
import cd.go.contrib.elasticagent.model.Field;
import cd.go.contrib.elasticagent.model.GoServerUrlField;
import cd.go.contrib.elasticagent.model.PositiveNumberField;
import cd.go.contrib.elasticagent.model.SecureURLField;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import static cd.go.contrib.elasticagent.utils.Util.GSON;

public class GetPluginConfigurationExecutor implements RequestExecutor {
    public static final Field GO_SERVER_URL = new GoServerUrlField("go_server_url", "Go Server URL", false, "0");
    public static final Field AUTOREGISTER_TIMEOUT = new PositiveNumberField("auto_register_timeout", "Agent auto-register Timeout (in minutes)", "10", true, false, "1");

    public static final Field KUBERNETES_CLUSTER_URL = new SecureURLField("kubernetes_cluster_url", "Kubernetes Cluster URL", true, "2");
    public static final Field KUBERNETES_CLUSTER_CLIENT_CERT = new Field("kubernetes_cluster_client_cert", "Kubernetes Client Certificate", null, false, false, "3");
    public static final Field KUBERNETES_CLUSTER_CLIENT_KEY = new Field("kubernetes_cluster_client_key", "Kubernetes Client Key", null, false, true, "4");

    public static final Field KUBERNETES_CLUSTER_USERNAME = new Field("kubernetes_cluster_username", "Kubernetes Cluster Username", null, false, false, "5");
    public static final Field KUBERNETES_CLUSTER_PASSWORD = new Field("kubernetes_cluster_password", "Kubernetes Cluster Password", null, false, true, "6");
    public static final Field KUBERNETES_CLUSTER_CA_CERT = new Field("kubernetes_cluster_ca_cert", "Kubernetes Cluster CA Certificate", null, false, true, "7");

    public static final Map<String, Field> FIELDS = new LinkedHashMap<>();

    static {
        FIELDS.put(GO_SERVER_URL.key(), GO_SERVER_URL);
        FIELDS.put(AUTOREGISTER_TIMEOUT.key(), AUTOREGISTER_TIMEOUT);

        FIELDS.put(KUBERNETES_CLUSTER_URL.key(), KUBERNETES_CLUSTER_URL);

        FIELDS.put(KUBERNETES_CLUSTER_CLIENT_CERT.key(), KUBERNETES_CLUSTER_CLIENT_CERT);
        FIELDS.put(KUBERNETES_CLUSTER_CLIENT_KEY.key(), KUBERNETES_CLUSTER_CLIENT_KEY);

        FIELDS.put(KUBERNETES_CLUSTER_USERNAME.key(), KUBERNETES_CLUSTER_USERNAME);
        FIELDS.put(KUBERNETES_CLUSTER_PASSWORD.key(), KUBERNETES_CLUSTER_PASSWORD);

        FIELDS.put(KUBERNETES_CLUSTER_CA_CERT.key(), KUBERNETES_CLUSTER_CA_CERT);
    }

    public GoPluginApiResponse execute() {
        return DefaultGoPluginApiResponse.success(GSON.toJson(FIELDS));
    }
}
