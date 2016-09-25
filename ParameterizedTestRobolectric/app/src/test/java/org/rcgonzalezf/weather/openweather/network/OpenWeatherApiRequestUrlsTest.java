package org.rcgonzalezf.weather.openweather.network;

import com.rcgonzalezf.parameterizedtestrobolectric.BuildConfig;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(ParameterizedRobolectricTestRunner.class) @Config(constants = BuildConfig.class, sdk = 23)
public class OpenWeatherApiRequestUrlsTest {

  /*
   * Parameters for each test case
   */
  private String mApiKey;
  private final OpenWeatherApiRequestParameters mOpenWeatherApiRequestParameters;

  /*
   * Unit Under Test
   */
  private OpenWeatherApiRequest uut;

  /*
   * Temp variables to hold the results to compare in each test case
   */
  private final String mExpectedUrl;
  private String mUrl;

  /**
   * Initializes Unit Under Test
   */
  @Before public void createApiRequestBaseObject() {
    mApiKey = "someApiKey";
    uut = new OpenWeatherApiRequest(mApiKey);
  }

  /**
   * Setting up the expectations, this constructor is going to be called by
   * the Custom Runner for Parameterized Tests
   *
   * @param expectedUrl  Value of the URL to assert
   * @param openWeatherApiRequestParameters Helper object to retrieve an url
   */
  public OpenWeatherApiRequestUrlsTest(String expectedUrl,
      OpenWeatherApiRequestParameters openWeatherApiRequestParameters) {
    mExpectedUrl = expectedUrl;
    mOpenWeatherApiRequestParameters = openWeatherApiRequestParameters;
  }

  /**
   * Prepares the parameters for the different test cases
   *
   * @return Collection with the parameters for each test case
   */
  @ParameterizedRobolectricTestRunner.Parameters public static Collection data() {

    return Arrays.asList(new Object[][] {
        {
            "http://api.openweathermap.org/data/2.5/forecast?q=London&type=like&APPID=someApiKey",
            createRequestParameterForCityName("London")
        }, {
        "http://api.openweathermap.org/data/2.5/forecast?lon=139.0&lat=35.0&APPID=someApiKey",
        createRequestParameterForLatLon(35d, 139d)
    }, {
        "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=someApiKey",
        createRequestParameterForCityId(524901)
    }, {
        "http://api.openweathermap.org/data/2.5/forecast?units=metric&id=524901&APPID=someApiKey",
        createRequestParameterForCityIdAndMetricUnits(524901)
    },
    });
  }

  @Test public void testCurrentUri() {
    // arrange
    givenRequestParameters(mOpenWeatherApiRequestParameters);
    // act
    whenGettingWeatherUrl();
    // assert
    thenUrlShouldBeExpected();
  }

  /*
   * Pattern that I like to use to express the test cases on a Unit Test
   * without having to add a BDD library.
   */
  private void thenUrlShouldBeExpected() {
    assertEquals(mExpectedUrl, mUrl);
  }

  private void whenGettingWeatherUrl() {
    mUrl = uut.url();
  }

  private void givenRequestParameters(
      OpenWeatherApiRequestParameters mOpenWeatherApiRequestParameters) {
    uut.addRequestParameters(mOpenWeatherApiRequestParameters);
  }

  /*
   * Helper methods for each test case
   */
  private static OpenWeatherApiRequestParameters createRequestParameterForCityIdAndMetricUnits(
      int cityId) {
    return new OpenWeatherApiRequestParameters.OpenWeatherApiRequestBuilder().withUnits(
        Units.METRIC).withCityId(cityId).build();
  }

  private static OpenWeatherApiRequestParameters createRequestParameterForCityId(int cityId) {
    return new OpenWeatherApiRequestParameters.OpenWeatherApiRequestBuilder().withCityId(cityId)
        .build();
  }

  private static OpenWeatherApiRequestParameters createRequestParameterForLatLon(Double lat,
      Double lon) {
    return new OpenWeatherApiRequestParameters.OpenWeatherApiRequestBuilder().withLatLon(lat, lon)
        .build();
  }

  private static OpenWeatherApiRequestParameters createRequestParameterForCityName(
      String cityName) {
    return new OpenWeatherApiRequestParameters.OpenWeatherApiRequestBuilder().withCityName(cityName)
        .build();
  }
}