/*
 * Copyright 2020 Avast Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.kork.retrofit.exceptions;

public class SpinnakerRetrofitErrorHandlerTest {
  /*
   * private static RetrofitService retrofitService;
   *
   * private static final MockWebServer mockWebServer = new MockWebServer();
   *
   * @BeforeAll public static void setupOnce() throws Exception { mockWebServer.start();
   * retrofitService = new
   * RestAdapter.Builder().setEndpoint(mockWebServer.url("/").toString()).setErrorHandler(
   * SpinnakerRetrofitErrorHandler.getInstance() ).build().create(RetrofitService.class); }
   *
   * @AfterAll public static void shutdownOnce() throws Exception { mockWebServer.shutdown(); }
   *
   * @Test public void testNotFoundIsNotRetryable() { mockWebServer.enqueue(new
   * MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value())); SpinnakerHttpException
   * notFoundException = assertThrows( SpinnakerHttpException.class, () -> retrofitService.getFoo() );
   * assertNotNull(notFoundException.getRetryable());
   * Assertions.assertFalse(notFoundException.getRetryable()); }
   *
   * @ParameterizedTest(name = "Deserialize response using {0}") // Test the different converters used
   * to deserialize the response body to the // SpinnakerServerException.RetrofitErrorResponseBody
   * class: // // - the JacksonConverter constructed without an ObjectMapper is used in //
   * Clouddriver's RestAdapter to communicate with Front50Service // // - the JacksonConverter
   * constructed with an ObjectMapper is used in Rosco's RestAdapter to // communicate with
   * Clouddriver // // - GSONConverter is the default converter used by Retrofit if no converter // is
   * set when building out the RestAdapter
   *
   * @ValueSource(strings = {"Default_GSONConverter", "JacksonConverter",
   * "JacksonConverterWithObjectMapper"}) public void testResponseWithExtraField(String
   * retrofitConverter) throws Exception { Map<String, String> responseBodyMap = new HashMap<>();
   * responseBodyMap.put("timestamp", "123123123123"); responseBodyMap.put("message",
   * "Not Found error Message"); String responseBodyString = new
   * ObjectMapper().writeValueAsString(responseBodyMap);
   *
   * RestAdapter.Builder restAdapter = new
   * RestAdapter.Builder().setEndpoint(mockWebServer.url("/").toString())
   * .setErrorHandler(SpinnakerRetrofitErrorHandler.getInstance());
   *
   * if (retrofitConverter.equals("JacksonConverter")) { restAdapter.setConverter(new
   * JacksonConverter()); } else if (retrofitConverter.equals("JacksonConverterWithObjectMapper")) {
   * ObjectMapper objectMapper = new
   * ObjectMapper().enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
   * .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
   *
   * restAdapter.setConverter(new JacksonConverter(objectMapper)); }
   *
   * RetrofitService retrofitServiceTestConverter = restAdapter.build().create(RetrofitService.class);
   *
   * mockWebServer.enqueue( new MockResponse().setBody(responseBodyString) // an arbitrary response
   * code -- one that // SpinnakerRetrofitErrorHandler converts to a // SpinnakerServerException (or
   * one of its children). .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()) );
   *
   * // If the converter can not deserialize the response body to the //
   * SpinnakerServerException.RetrofitErrorResponseBody // class, then a RuntimeException will be
   * thrown with a ConversionException nested inside. // // java.lang.RuntimeException: //
   * retrofit.converter.ConversionException: //
   * com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field // "..." //
   * // so make sure we get a SpinnakerHttpException from calling getFoo
   * assertThrows(SpinnakerHttpException.class, retrofitServiceTestConverter::getFoo); }
   *
   * @Test public void testBadRequestIsNotRetryable() { mockWebServer.enqueue(new
   * MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value())); SpinnakerHttpException
   * spinnakerHttpException = assertThrows( SpinnakerHttpException.class, () ->
   * retrofitService.getFoo() ); assertNotNull(spinnakerHttpException.getRetryable());
   * Assertions.assertFalse(spinnakerHttpException.getRetryable()); }
   *
   * @Test public void testOtherClientErrorHasNullRetryable() { // Arbitrarily choose GONE as an
   * example of a client (e.g. 4xx) error that // we expect to have null retryable
   * mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.GONE.value()));
   * SpinnakerHttpException spinnakerHttpException = assertThrows( SpinnakerHttpException.class, () ->
   * retrofitService.getFoo() ); Assertions.assertNull(spinnakerHttpException.getRetryable()); }
   *
   * @Test public void testSimpleSpinnakerNetworkException() { String message = "my custom message";
   * IOException e = new IOException(message); RetrofitError retrofitError =
   * RetrofitError.networkError("http://localhost", e); SpinnakerRetrofitErrorHandler handler =
   * SpinnakerRetrofitErrorHandler.getInstance(); Throwable throwable =
   * handler.handleError(retrofitError); Assertions.assertEquals(message, throwable.getMessage()); }
   *
   * interface RetrofitService {
   *
   * @GET("/foo") Response getFoo(); }
   */
}
