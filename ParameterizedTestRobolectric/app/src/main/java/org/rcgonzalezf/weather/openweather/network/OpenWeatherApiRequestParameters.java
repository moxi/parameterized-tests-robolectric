package org.rcgonzalezf.weather.openweather.network;

import java.util.HashMap;
import java.util.Map;
import org.rcgonzalezf.weather.common.network.RequestParameters;

public class OpenWeatherApiRequestParameters implements RequestParameters<String> {

  private String mQueryString;
  private String mLat;
  private String mLon;
  private String mCityName;
  private Integer mCityId;
  private Units mUnits;

  @Override public Map<String, String> getKeyValueParameters() {
    throw new UnsupportedOperationException("This service doesn't provides this method");
  }

  @Override  public String getQueryString() {
    return mQueryString;
  }

  public void setQueryString(String queryString) {
    mQueryString = queryString;
  }

  public String getLat() {
    return mLat;
  }

  public void setLat(String lat) {
    mLat = lat;
  }

  public String getLon() {
    return mLon;
  }

  public void setLon(String lon) {
    mLon = lon;
  }

  public String getCityName() {
    return mCityName;
  }

  public void setCityName(String cityName) {
    mCityName = cityName;
  }

  public Integer getCityId() {
    return mCityId;
  }

  public void setCityId(Integer cityId) {
    this.mCityId = cityId;
  }

  public Units getUnits() {
    return mUnits;
  }

  public void setUnits(Units units) {
    mUnits = units;
  }

  public static class OpenWeatherApiRequestBuilder {

    public static final String LON = "lon";
    public static final String LAT = "lat";
    public static final String CITY_ID = "id";
    public static final String CITY_NAME = "q";
    public static final String TYPE = "type";
    public static final String LIKE = "like";
    public static final String UNITS = "units";
    private final OpenWeatherApiRequestParameters mOpenWeatherApiRequestParameters;

    private Map<String, String> mQueryParametersMap;

    public OpenWeatherApiRequestBuilder() {
      mQueryParametersMap = new HashMap<>();
      mOpenWeatherApiRequestParameters = new OpenWeatherApiRequestParameters();
    }

    public OpenWeatherApiRequestBuilder withCityId(Integer cityId) {
      mQueryParametersMap.put(CITY_ID, String.valueOf(cityId));
      mOpenWeatherApiRequestParameters.setCityId(cityId);
      return this;
    }

    public OpenWeatherApiRequestParameters build() {
      if (mQueryParametersMap.isEmpty()) {
        throw new IllegalStateException("Can't prepare empty parameters");
      }
      StringBuilder queryBuilder = new StringBuilder();

      int i = 0, size = mQueryParametersMap.size();

      for (String key : mQueryParametersMap.keySet()) {
        queryBuilder.append(key).append("=").append(mQueryParametersMap.get(key));

        boolean hasMore = i + 1 < size;
        if (hasMore) {
          queryBuilder.append("&");
        }
        ++i;
      }

      mOpenWeatherApiRequestParameters.setQueryString(queryBuilder.toString());
      return mOpenWeatherApiRequestParameters;
    }

    private OpenWeatherApiRequestBuilder withLat(Double lat) {
      mQueryParametersMap.put(LAT, String.valueOf(lat));
      mOpenWeatherApiRequestParameters.setLat(String.valueOf(lat));
      return this;
    }

    private OpenWeatherApiRequestBuilder withLon(Double lon) {
      mQueryParametersMap.put(LON, String.valueOf(lon));
      mOpenWeatherApiRequestParameters.setLon(String.valueOf(lon));
      return this;
    }

    public OpenWeatherApiRequestBuilder withLatLon(Double lat, Double lon) {
      return withLat(lat).withLon(lon);
    }

    public OpenWeatherApiRequestBuilder withCityName(String cityName) {
      mQueryParametersMap.put(CITY_NAME, cityName);
      mQueryParametersMap.put(TYPE, LIKE);
      mOpenWeatherApiRequestParameters.setCityName(cityName);
      return this;
    }

    public OpenWeatherApiRequestBuilder withUnits(Units units) {
      mQueryParametersMap.put(UNITS, units.getUnitName());
      mOpenWeatherApiRequestParameters.setUnits(units);
      return this;
    }
  }
}
