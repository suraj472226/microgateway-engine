package com.suraj.gateway.models;

import com.suraj.gateway.enums.HttpMethod;

import java.util.Collections;
import java.util.Map;

public class Request {

	private final HttpMethod method;

	private final String path;

	private final Map<String, String> headers;

	private final Map<String, String> queryParams;

	private final String body;

	private final String ipAddress;

	private final Map<String, String> pathVariables;

	private Request(
			HttpMethod method,
			String path,
			Map<String, String> headers,
			Map<String, String> queryParams,
			String body,
			String ipAddress,
			Map<String, String> pathVariables
	) {
		this.method = method;
		this.path = path;
		this.headers = Collections.unmodifiableMap(headers);
		this.queryParams = Collections.unmodifiableMap(queryParams);
		this.body = body;
		this.ipAddress = ipAddress;
		this.pathVariables = Collections.unmodifiableMap(pathVariables);
	}


	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public String getBody() {
		return body;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Map<String, String> getPathVariables() {
		return pathVariables;
	}

	public Builder toBuilder() {

		Builder builder = new Builder();

		builder.method = this.method;

		builder.path = this.path;

		builder.headers = this.headers;

		builder.queryParams = this.queryParams;

		builder.body = this.body;

		builder.ipAddress = this.ipAddress;

		builder.pathVariables = this.pathVariables;

		return builder;
	}

	public static class Builder {

		private HttpMethod method;

		private String path;

		private Map<String, String> headers = Map.of();

		private Map<String, String> queryParams = Map.of();

		private String body = "";

		private String ipAddress;

		private Map<String, String> pathVariables = Map.of();

		public Builder method(HttpMethod method) {
			this.method = method;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public Builder headers(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		public Builder queryParams(Map<String, String> queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public Builder body(String body) {
			this.body = body;
			return this;
		}

		public Builder ipAddress(String ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}

		public Builder pathVariables(Map<String, String> pathVariables) {
			this.pathVariables = pathVariables;
			return this;
		}

		public Request build() {

			if(method == null) {
				throw new IllegalStateException("HTTP Method is required");
			}

			if(path == null || path.isBlank()) {
				throw new IllegalStateException("Path is required");
			}

			if(ipAddress == null || ipAddress.isBlank()) {
				throw new IllegalStateException("IP Address is required");
			}
			return new Request(
					method,
					path,
					headers,
					queryParams,
					body,
					ipAddress,
					pathVariables
			);
		}
	}
}