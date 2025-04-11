# Import numpy <- You will see this line a lot
# Import time for testing function
import time

# Import typing support
from typing import List

import matplotlib.pyplot as plt
import numpy as np

# And our custom utilities library
from utils import problem

Vector = List[float]
Matrix = List[Vector]


def vanilla_matmul(A: Matrix, x: Vector) -> Vector:
    """Performs a matrix multiplications Ax on vanilla python lists.

    Args:
        A (Matrix): a (n, d) matrix.
        x (Vector): a (d,) vector.

    Returns:
        Vector: a resulting (n,) vector.

    Note:
        In this problem specifically d = n, since A and B are square matrices.
    """
    result: Vector = []
    for a in A:
        result_a = 0.0
        for a_i, x_i in zip(a, x):
            result_a += a_i * x_i
        result.append(result_a)
    return result


def vanilla_transpose(A: Matrix) -> Matrix:
    """Performs a matrix transpose

    Args:
        A (Matrix): a (n, d) matrix.

    Returns:
        Matrix: a resulting (d, n) matrix.
    """
    result: Matrix = [[] for _ in A[0]]  # Create list of d lists
    for a in A:
        for jdx, value in enumerate(a):
            result[jdx].append(value)
    return result


@problem.tag("hw0-A")
def vanilla_solution(x: Vector, y: Vector, A: Matrix, B: Matrix) -> Vector:
    """Calculates gradient of f(x, y) with respect to x using vanilla python lists.
    Where $$f(x, y) = x^T A x + y^T B x + c$$

    Args:
        x (Vector): a (n,) vector.
        y (Vector): a (n,) vector.
        A (Matrix): a (n, n) matrix.
        B (Matrix): a (n, n) matrix.

    Returns:
        Vector: a resulting (n,) vector.

    Note:
        - We provided you with `vanilla_transpose` and `vanilla_matmul` functions which you should use.
        - In this context (and documentation of two functions above) vector means list of floats,
            and matrix means list of lists of floats
    """
    # gradient of f(x, y) with respect to x: delX = (A + A^T)x + (B^T)y
    ATranspose = vanilla_transpose(A)
    BTranspose = vanilla_transpose(B)
    # add A with A^T
    first_vector = vanilla_matmul(vanilla_matrix_sum(A, ATranspose), x)
    # multiply B^T with y
    second_vector = vanilla_matmul(BTranspose, y)
    # return the sum of the 2 vectors
    return vanilla_vector_sum(first_vector, second_vector)


def vanilla_vector_sum(a: Vector, b: Vector) -> Matrix:
    """Performs vector addition

    Args:
        a (Vector): a (n,) vector.
        b (Vector): a (n,) vector.

    Returns:
        Vector: a resulting (n,) vector.
    """
    n = len(a)
    res_vector = [0 for _ in range(n)]
    for i in range(n):
        res_vector[i] = a[i] + b[i]
    return res_vector


def vanilla_matrix_sum(A: Matrix, B: Matrix) -> Matrix:
    """Performs matrix summation

    Args:
        A (Matrix): a (n, d) matrix.
        B (Matrix): a (n, d) matrix.

    Returns:
        Matrix: a resulting (n, d) matrix.

    Note:
        In this problem specifically d = n, since A and B are square matrices.
    """
    n = len(A)
    d = len(A[0])
    res_matrix = [[0 for _ in range(d)] for _ in range(n)]
    for i in range(n):
        for j in range(n):
            res_matrix[i][j] = A[i][j] + B[i][j]
    return res_matrix


@problem.tag("hw0-A")
def numpy_solution(
    x: np.ndarray, y: np.ndarray, A: np.ndarray, B: np.ndarray
) -> np.ndarray:
    """Calculates gradient of f(x, y) with respect to x using numpy arrays.
    Where $$f(x, y) = x^T A x + y^T B x + c$$

    Args:
        x (np.ndarray): a (n,) numpy array.
        y (np.ndarray): a (n,) numpy array.
        A (np.ndarray): a (n, n) numpy array.
        B (np.ndarray): a (n, n) numpy array.

    Returns:
        np.ndarray: a resulting (n, ) numpy array.

    Note:
        - Make use of numpy docs: https://numpy.org/doc/
            You will use this link a lot throughout quarter, so it might be a good idea to bookmark it!
    """
    # gradient of f(x, y) with respect to x: delX = (A + A^T)x + (B^T)y
    ATranspose = np.transpose(A)
    BTranspose = np.transpose(B)
    # add A with A^T
    first_vector = np.matmul(np.add(A, ATranspose), x)
    # multiply B^T with y
    second_vector = np.matmul(BTranspose, y)
    # return the sum of the 2 vectors
    return np.add(first_vector, second_vector)


def main():
    """
    Before running this file run `inv test` to make sure that your numpy and vanilla solutions are correct.
    """
    RNG = np.random.RandomState(seed=446)

    ns = [20, 200, 500, 1000]
    vanilla_times = []
    numpy_times = []

    for n in ns:
        # Generate some data
        x = RNG.randn(n)
        y = RNG.randn(n)
        A = RNG.randn(n, n)
        B = RNG.randn(n, n)
        # And their vanilla List equivalents
        x_list = x.tolist()
        y_list = y.tolist()
        A_list = A.tolist()
        B_list = B.tolist()

        start = time.time_ns()
        vanilla_result = vanilla_solution(x_list, y_list, A_list, B_list,)
        vanilla_time = time.time_ns() - start
        start = time.time_ns()
        numpy_result = numpy_solution(x, y, A, B)
        numpy_time = time.time_ns() - start

        np.testing.assert_almost_equal(vanilla_result, numpy_result)

        print(f"Time for vanilla implementation: {vanilla_time / 1e6}ms")
        print(f"Time for numpy implementation: {numpy_time / 1e6}ms")

        vanilla_times.append(vanilla_time)
        numpy_times.append(numpy_time)

    plt.plot(ns, vanilla_times, label="Vanilla")
    plt.plot(ns, numpy_times, label="Numpy")
    plt.xlabel("n")
    plt.ylabel("Time (ns)")
    plt.legend()
    plt.show()


if __name__ == "__main__":
    main()
