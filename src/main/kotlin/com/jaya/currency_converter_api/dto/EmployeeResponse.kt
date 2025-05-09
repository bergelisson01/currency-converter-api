package com.jaya.currency_converter_api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserMockResponse(
    @JsonProperty("id") var id: Long?,
    @JsonProperty("username") var name: String?,
    @JsonProperty("email") var email: String?,
    @JsonProperty("session") var session: Long?,
)

data class Employee(
    @JsonProperty("employee_name") var id: String?,
    @JsonProperty("employee_name") var name: String?,
    @JsonProperty("employee_salary") var salary: String?,
    @JsonProperty("employee_age") var age: String?,
    @JsonProperty("profile_image") var profileImage: String?
)