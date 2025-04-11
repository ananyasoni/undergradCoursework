// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/08/2025
// ex3

#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

// the following defines a new
// structured datatype called
// a "struct Point3d_st"
// which is a 3-dimensional
// coordinate point with
// x, y, and z values
// make "Point3d" a synonym for
// "struct Point3d_st" and "Point3dPtr"
// a synonym for "struct Point3d_st*"
typedef struct Point3d_st {
    uint16_t x;
    uint16_t y;
    uint16_t z;
} Point3d, *Point3dPtr;

// Behavior:
//   -  accepts three values as arguments, allocates space for a
//      Point3d, assigns those three arguments to Point3d, and
//      returns (a pointer to) the Point3d
// Parameters:
//   - uint16_t x: x value assigned to Point3d
//   - uint16_t y: y value assigned to Point3d
//   - uint16_t z: z value assigned to Point3d
// Returns:
//  - Point3dPtr: a pointer to the allocated
//    Point3d
// Exceptions/Errors:
//  - failed to allocate memory for Point3d
Point3dPtr AllocatePoint3d(uint16_t x, uint16_t y, uint16_t z);

int main(int argc, char **argv) {
    Point3dPtr point1 = AllocatePoint3d(1, 2, 3);
    Point3dPtr point2 = AllocatePoint3d(0, 0, 0);
    Point3dPtr point3 = AllocatePoint3d(5, 10, 15);
    if (point1 == NULL || point2 == NULL || point3 == NULL) {
        printf("failed to allocate memory for Point3d\n");
        return EXIT_FAILURE;
    }
    if (point1 -> x != 1 || point1 -> y != 2 || point1 -> z != 3) {
        printf("failed test case 1 (x,y,z) should equal (1,2,3)\n");
        return EXIT_FAILURE;
    } else if (point2 -> x != 0 || point2 -> y != 0 || point2 -> z != 0) {
        printf("failed test case 2 (x,y,z) should equal (0,0,0)\n");
        return EXIT_FAILURE;
    } else if (point3 -> x != 5 || point3 -> y != 10 || point3 -> z != 15) {
        printf("failed test case 3 (x,y,z) should equal (5,10,15)\n");
        return EXIT_FAILURE;
    }
    free(point1);
    free(point2);
    free(point3);
    return EXIT_SUCCESS;
}

Point3dPtr AllocatePoint3d(uint16_t x, uint16_t y, uint16_t z) {
    Point3dPtr point3d = (Point3dPtr) malloc(sizeof(Point3d));
    if (point3d != NULL) {
        point3d -> x = x;
        point3d -> y = y;
        point3d -> z = z;
    } else {
        fprintf(stderr, "failed to allocate memory for Point3d\n");
    }
    return point3d;
}
