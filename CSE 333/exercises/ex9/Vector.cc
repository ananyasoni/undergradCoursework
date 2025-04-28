// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/28/2025
// ex9

#include <iostream>
#include <sstream>
#include <string>

#include "Vector.h"

namespace vector333 {

Vector::Vector() {
  this->x_ = 0;
  this->y_ = 0;
  this->z_ = 0;
}

Vector::Vector(const double x, const double y, const double z) {
  this->x_ = x;
  this->y_ = y;
  this->z_ = z;
}

Vector::Vector(const Vector &copyme) {
  this->x_ = copyme.x_;
  this->y_ = copyme.y_;
  this->z_ = copyme.z_;
}

Vector::~Vector() {}

Vector &Vector::operator=(const Vector &v) {
  if (this != &v) {
    this->x_ = v.x_;
    this->y_ = v.y_;
    this->z_ = v.z_;
  }
  return *this;
}

Vector &Vector::operator+=(const Vector &v) {
  this->x_ += v.x_;
  this->y_ += v.y_;
  this->z_ += v.z_;
  return *this;
}

Vector &Vector::operator-=(const Vector &v) {
  this->x_ -= v.x_;
  this->y_ -= v.y_;
  this->z_ -= v.z_;
  return *this;
}

// additional non-member overloaded operators in vector333 namespace
Vector operator+(const Vector &u, const Vector &v) {
  double x = u.get_x() + v.get_x();
  double y = u.get_y() + v.get_y();
  double z = u.get_z() + v.get_z();
  return Vector(x, y, z);
}

Vector operator-(const Vector &u, const Vector &v) {
  double x = u.get_x() - v.get_x();
  double y = u.get_y() - v.get_y();
  double z = u.get_z() - v.get_z();
  return Vector(x, y, z);
}

// computes and returns the dot product of u and v
double operator*(const Vector &u, const Vector &v) {
  double u_x = u.get_x();
  double u_y = u.get_y();
  double u_z = u.get_z();
  double v_x = v.get_x();
  double v_y = v.get_y();
  double v_z = v.get_z();
  return u_x * v_x + u_y * v_y + u_z * v_z;
}

Vector operator*(const double k, const Vector &v) {
  double v_x =  v.get_x();
  double v_y = v.get_y();
  double v_z = v.get_z();
  return Vector(k * v_x, k * v_y, k * v_z);
}

Vector operator*(const Vector &v, const double k) {
  return operator*(k, v);
}

std::ostream &operator<<(std::ostream &out, const Vector &v) {
  out << "(" << v.get_x() << "," << v.get_y() << "," << v.get_z() << ")";
  return out;
}

}  // namespace vector333
