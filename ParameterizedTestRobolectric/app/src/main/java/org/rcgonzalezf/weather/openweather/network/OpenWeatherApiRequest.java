package org.rcgonzalezf.weather.openweather.network;

import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.rcgonzalezf.weather.common.network.ApiCallback;
import org.rcgonzalezf.weather.common.network.ApiRequest;

public class OpenWeatherApiRequest implements ApiRequest<OpenWeatherApiRequestParameters> {

  private static final String TAG = OpenWeatherApiRequest.class.getSimpleName();
  private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
  private static final String FORECAST = "forecast";
  private static final String URL_FORMAT = "%1$s%2$s?%3$s&APPID=%4$s";
  private final String mApiKey;
  private OpenWeatherApiRequestParameters mRequestParameters;

  public OpenWeatherApiRequest(String apiKey) {
    mApiKey = apiKey;
  }

  @Override public String getBaseUrl() {
    return BASE_URL;
  }

  @Override public String getMethodName() {
    return FORECAST;
  }

  @Override public void execute(ApiCallback apiCallback) {
    getExecutor().execute(new Runnable() {
      @Override public void run() {
        Log.d(TAG, "Executing the Request to url: " + url());
      }
    });
  }

  @Override public void addRequestParameters(OpenWeatherApiRequestParameters requestParameters) {
    mRequestParameters = requestParameters;
  }

  protected String url() {
    return String.format(URL_FORMAT, getBaseUrl(), getMethodName(),
        mRequestParameters.getQueryString(), mApiKey);
  }

  protected Executor getExecutor() {
    return Executors.newSingleThreadExecutor();
  }
}
