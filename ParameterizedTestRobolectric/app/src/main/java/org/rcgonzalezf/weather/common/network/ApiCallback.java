package org.rcgonzalezf.weather.common.network;

public interface ApiCallback<T, E> {
  void onSuccess(T apiResponse);
  void onError(E apiError);
}
