// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/28/2025
// ex9

#ifndef _VECTOR_H_
#define _VECTOR_H_

#include <iostream>

namespace vector333 {

class Vector {
 public:
  // Default (0-arguement) Constructor
  Vector();
  // Overloaded Constructor
  Vector(const double x, const double y, const double z);
  // Copy constructor
  Vector(const Vector& copyme);
  // Destructor
  ~Vector();

  // Accessors defined inline.
  double get_x() const { return x_; }
  double get_y() const { return y_; }
  double get_z() const { return z_; }

  // Override the "=" operator
  Vector &operator=(const Vector &v);
  // Override the "+=" operator
  Vector &operator+=(const Vector &v);
  // Override the "-=" operator
  Vector &operator-=(const Vector &v);

 private:
  double x_, y_, z_;  // Member variables.
};

// additional overloaded operators (not member or friend functions)
Vector operator+(const Vector &u, const Vector &v);
Vector operator-(const Vector &u, const Vector &v);
double operator*(const Vector &u, const Vector &v);
Vector operator*(const double k, const Vector &v);
Vector operator*(const Vector &v, const double k);
std::ostream &operator<<(std::ostream &out, const Vector &a);

}  // namespace vector333

#endif  // _VECTOR_H_
