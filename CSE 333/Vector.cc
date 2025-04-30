// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/30/2025
// ex10

#include "Vector.h"

#include <iostream>
using std::ostream;

namespace vector333 {

// Default: construct the vector (0,0,0)
Vector::Vector() {
  xyz_ = new double[3];
  xyz_[0] = 0;
  xyz_[1] = 0;
  xyz_[2] = 0;
}

// Construct the vector (x,y,z)
Vector::Vector(const double x, const double y, const double z) {
  xyz_ = new double[3];
  xyz_[0] = x;
  xyz_[1] = y;
  xyz_[2] = z;
}

// Copy constructor
Vector::Vector(const Vector &v) {
  xyz_ = new double[3];
  xyz_[0] = v.xyz_[0];
  xyz_[1] = v.xyz_[1];
  xyz_[2] = v.xyz_[2];
}

// Destructor
Vector::~Vector() {
  delete [] xyz_;
}

// Vector assignment
Vector &Vector::operator=(const Vector &rhs) {
  if (this != &rhs) {
    this -> xyz_[0] = rhs.xyz_[0];
    this -> xyz_[1] = rhs.xyz_[1];
    this -> xyz_[2] = rhs.xyz_[2];
  }
  // return reference to lhs of assignment
  return *this;
}

// Updating assignments for vectors

Vector &Vector::operator+=(const Vector &rhs) {
  xyz_[0] += rhs.xyz_[0];
  xyz_[1] += rhs.xyz_[1];
  xyz_[2] += rhs.xyz_[2];
  return *this;
}

Vector &Vector::operator-=(const Vector &rhs) {
  xyz_[0] -= rhs.xyz_[0];
  xyz_[1] -= rhs.xyz_[1];
  xyz_[2] -= rhs.xyz_[2];
  return *this;
}

// Vector addition
Vector operator+(const Vector &a, const Vector &b) {
  Vector tmp = a;
  tmp += b;
  return tmp;
}

// Vector subtraction
Vector operator-(const Vector &a, const Vector &b) {
  Vector tmp = a;
  tmp -= b;
  return tmp;
}

// dot-product: if a is (a,b,c) and b is (x,y,z),
// return ax+by+cz
double operator*(const Vector &a, const Vector &b) {
  return a.xyz_[0]*b.xyz_[0] + a.xyz_[1]*b.xyz_[1] + a.xyz_[2]*b.xyz_[2];
}

// scalar multiplication: if v is (a,b,c), return (ak,bk,ck)
Vector operator*(const double k, const Vector &v) {
  return Vector(v.xyz_[0] * k, v.xyz_[1] * k, v.xyz_[2] * k);
}
Vector operator*(const Vector &v, const double k) {
  return Vector(v.xyz_[0] * k, v.xyz_[1] * k, v.xyz_[2] * k);
}

// Stream output: << for Vectors
ostream &operator<<(ostream &out, const Vector &v) {
  out << "(" << v.xyz_[0] << "," << v.xyz_[1] << "," << v.xyz_[2] << ")";
  return out;
}

}  // namespace vector333
