package org.scu.srg.models;

public class Employee {
  private String name;
  private String email;
  private EmployeeType employeeType;

  public Employee(String name, String email, EmployeeType employeeType) {
    this.name = name;
    this.email = email;
    this.employeeType = employeeType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public EmployeeType getEmployeeType() {
    return employeeType;
  }

  public void setEmployeeType(EmployeeType employeeType) {
    this.employeeType = employeeType;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", employeeType=" + employeeType +
        '}';
  }
}
