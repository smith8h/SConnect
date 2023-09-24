/*
 * Copyright (C) 2023, Hussein Shakir (Dev. Smith)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package smith.lib.net;

import java.util.Map;

/**
 * Use it to interact with SConnect connections.
 */
public interface SConnectCallBack {
	/**
	 * Triggered when connection is done successfully.
	 * @param response The response got from the connection result.
	 * @param tag The unique tag that passed to the connection to distinguish it.
	 * @param responseHeaders The headers passed to the connection.
	 */
	void onSuccess(SResponse response, String tag, Map<String, Object> responseHeaders);

	/**
	 * Triggered when connection is not done or some errors happened.
	 * @param response The response got from the connection result.
	 * @param tag The unique tag that passed to the connection to distinguish it.
	 */
	void onFailure(SResponse response, String tag);
}
