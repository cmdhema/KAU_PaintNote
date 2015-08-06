package kau.paintnote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkModel {

	private static NetworkModel instance;

	public HashMap<Context, ArrayList<NetworkRequest>> mRequestMap = new HashMap<Context, ArrayList<NetworkRequest>>();

	public static final int MAX_THREAD_COUNT = 5;

	public static NetworkModel getInstance() {
		if (instance == null)
			instance = new NetworkModel();

		return instance;
	}

	public NetworkModel() {

	}

	public void getNetworkData(Context context, NetworkRequest request) {
		request.context = context;
		ArrayList<NetworkRequest> list = mRequestMap.get(context);

		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);

		new GetInfoAsyncTask(request).execute(request);

	}

	public void sendNetworkData(Context context, NetworkRequest request) {
		request.context = context;
		ArrayList<NetworkRequest> list = mRequestMap.get(context);

		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);

		new SendToServerAsyncTask(request).execute(request);

	}


	public void cleanUpRequest(Context context) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		if (list != null) {
			for (NetworkRequest request : list) {
				request.setCancel();
			}
		}
	}

	private class GetInfoAsyncTask extends AsyncTask<NetworkRequest, Integer, Boolean> {

		NetworkRequest mRequest;

		public GetInfoAsyncTask(NetworkRequest request) {
			mRequest = request;
		}

		@Override
		protected Boolean doInBackground(NetworkRequest... params) {
			NetworkRequest request = params[0];
			mRequest = request;
			URL url = request.getURL();
			int retry = 3;

			while (retry > 0) {
				try {
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod(request.getRequestMethod());
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					System.setProperty("http.keepAlive", "false");
					if (request.isCancel())
						return false;

					int resCode = conn.getResponseCode();
					if (request.isCancel())
						return false;

					if (resCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();

						request.process(is);
						return true;
					} else
						return false;
				} catch (IOException e) {
					e.printStackTrace();
				}

				retry--;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result)
				mRequest.sendResult();
			else
				mRequest.sendError(0);

			ArrayList<NetworkRequest> list = mRequestMap.get(mRequest.context);
			list.remove(mRequest);

			super.onPostExecute(result);
		}

	}
	
	private class SendToServerAsyncTask extends AsyncTask<NetworkRequest, Integer, Boolean> {

		PostNetworkRequest mRequest;

		public SendToServerAsyncTask(NetworkRequest request) {
//			mRequest = request;
		}

		@Override
		protected Boolean doInBackground(NetworkRequest... params) {

			NetworkRequest request = params[0];
			mRequest = (PostNetworkRequest) request;
			URL url = request.getURL();
			int retry = 3;

			while (retry > 0) {
				try {
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					conn.setRequestMethod(request.hgetRequestMethod());
					conn.setConnectTimeout(request.getConnectionTimeout());
//					conn.setRequestProperty("Content-Type", "application/json");
//					conn.setReadTimeout(request.getReadTimeout());
//					conn.setDoOutput(true);
//					request.setRequestProertpy(conn);
//					request.setOutput(conn);
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setDoOutput(true);
					OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
					Log.i("NetworkModel", mRequest.getRequestQueryString());
					wr.write(mRequest.getRequestQueryString());
					wr.flush();
					wr.close();

					if (request.isCancel())
						return false;

					int resCode = conn.getResponseCode();
					if (request.isCancel())
						return false;
					if (resCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
						Log.i("Send Object", "Success");
						return true;
					} else {
						Log.i("Send Object", resCode + "");
//						Log.e("JsonTest", mObject.toString());
						return false;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				retry--;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result)
				mRequest.sendResult();
			else
				mRequest.sendError(0);

			ArrayList<NetworkRequest> list = mRequestMap.get(mRequest.context);
			list.remove(mRequest);

			super.onPostExecute(result);
		}
	}

}
