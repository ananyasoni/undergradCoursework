import os
import csv
import numpy as np
import math
from operator import itemgetter

from sys import platform as sys_pf
if sys_pf == 'darwin':
    import matplotlib
    matplotlib.use("TkAgg")

import matplotlib.pyplot as plt  # noqa: E402


def check_approx_equals(expected, received):
    """Checks received against expected, and returns whether or
    not they match (True if they do, False otherwise).
    If the argument is a float, will do an approximate check.
    If the arugment is a data structure will do an approximate check
    on all of its contents.

    Arguments:
        expected: the expected value
        received: the received value

    Returns: True if the received match the expected, False otherwise
    """

    try:
        if type(expected) == dict:
            # first check that keys match, then check that the
            # values approximately match
            if type(list(expected.values())[0][0]) == list:
                # handle list of list as value, need to sort first
                return expected.keys() == received.keys() and \
                    all([check_approx_equals(
                        sorted(expected[k], key=itemgetter(0)),
                        sorted(received[k], key=itemgetter(0)))
                        for k in expected.keys()])
            else:
                return expected.keys() == received.keys() and \
                    all([check_approx_equals(expected[k], received[k])
                        for k in expected.keys()])
        elif type(expected) == list or type(expected) == set:
            # Checks both lists/sets contain the same values
            return len(expected) == len(received) and \
                all([check_approx_equals(v1, v2)
                    for v1, v2 in zip(expected, received)])
        elif type(expected) == float:
            return math.isclose(expected, received, abs_tol=0.0001)
        else:
            return expected == received
    except Exception as e:
        print(f'EXCEPTION: Raised when checking check_approx_equals {e}')
        return False


def assert_equals(expected, received):
    """Checks received against expected, throws an AssertionError
    if they don't match. If the argument is a float, will do an approximate
    check. If the arugment is a data structure will do an approximate check
    on all of its contents.

    Arguments:
        expected: the expected value
        received: the received value
    """
    assert check_approx_equals(expected, received), \
        f'Failed: Expected {expected}, but received {received}'


def read_data(fname):
    data = []
    label = []
    with open(fname, newline='') as f:
        reader = csv.reader(f, delimiter=',')
        for r in reader:
            label.append(int(float(r[0])))
            data.append(list(map(float, r[1:])))
    return data, label


def load_centroids(fname):
    centroids = dict()
    with open(fname, newline='') as f:
        reader = csv.reader(f, delimiter=',')
        for i, r in enumerate(reader):
            centroids[f"centroid{i}"] = list(map(float, r))
    return centroids


def write_centroids_tofile(fname, centroids):
    centroid_values = np.asarray(list(centroids.values()))
    np.savetxt(fname, centroid_values, delimiter=',')


def plot_2d(assignment_dict, centroids):
    fig = plt.figure()
    colors = {"centroid0": "blue", "centroid1": "red"}
    for k in assignment_dict.keys():
        v = np.array(assignment_dict[k])
        plt.scatter(v[:, 0], v[:, 1], marker='o', s=15, c=colors[k])
        plt.scatter(centroids[k][0], centroids[k][1], marker='x', s=100,
                    c=colors[k], label=k)
    plt.xlim(-2, 5)
    plt.ylim(-2, 6)
    plt.legend()
    return fig


def plot_digit(digit):
    assert len(digit) == 784
    # mnist digits are size 28 x 28
    im = np.array(digit).reshape(28, 28)
    fig = plt.figure()
    plt.imshow(im, cmap='gray')
    return fig


def plot_centroids(centroids, name):
    for k, v in centroids.items():
        fig = plot_digit(v)
        fig.savefig(os.path.join("results", "MNIST", name, f"{k}.png"))


def converged(c1, c2):
    if c1 is None or c2 is None:
        return False
    conv = True
    for key in c1.keys():
        conv = conv and np.allclose(np.array(c1[key]), np.array(c2[key]))
    return conv
