"""
* Name: Ananya Shreya Soni
* Date: 02/10/2024
* CSE 160, Winter 2023
* Homework 4
* Description: This class implements the K-Means
* Clustering Algorithm and applies it to a given
* Dataset.
"""

import utils  # noqa: F401
import matplotlib.pyplot as plt  # noqa: E402
import os  # noqa: E402
import math  # noqa: E402
from utils import converged, plot_2d, plot_centroids, assert_equals, \
    read_data, load_centroids, write_centroids_tofile  # noqa: E402


def euclidean_distance(point1, point2):
    """Calculate the Euclidean distance between two data points.

    Arguments:
        point1: a non-empty list of floats representing a data point
        point2: a non-empty list of floats representing a data point

    Returns: the Euclidean distance between two data points
    """
    # initialize distance to 0
    # loop through the point lengths
    # (number of components in each point)
    # add the ith component in point 1 minus the
    # ith component in point 2 squared
    # after looping through all coordinates take the
    # square root and return the distance
    distance = 0
    for i in range(len(point1)):
        distance += ((point1[i] - point2[i]) ** 2)
    distance = distance ** (1/2)
    return distance


def get_closest_centroid(point, centroids_dict):
    """Given a datapoint, finds the closest centroid. You should use
    the euclidean_distance function (that you previously implemented).

    Arguments:
        point: a list of floats representing a data point
        centroids_dict: a dictionary representing the centroids where the keys
                        are strings (centroid names) and the values are lists
                        of centroid locations

    Returns: a string as the key name of the closest centroid to the data point
    """
    closestCentroid = ""
    distances = list(centroids_dict.values())
    minDistance = euclidean_distance(point, distances[0]) + 1
    for nextCentroid in centroids_dict.keys():
        nextDist = euclidean_distance(point, centroids_dict.get(nextCentroid))
        # update the minimum distance and closest centroid
        # only if the next found euclidean distance is
        # less than the previously stored minimum
        # distance
        if (nextDist < minDistance):
            minDistance = nextDist
            closestCentroid = nextCentroid
    return closestCentroid


# problem for students
def assign_update(list_of_points, centroids_dict):
    """Assign all data points to the closest centroids. You should use
    the get_closest_centroid function (that you previously implemented).
    This function should return a new dictionary, not modify any
    passed in parameters.

    Arguments:
        list_of_points: a list of lists representing all data points
        centroids_dict: a dictionary representing the centroids where the keys
                        are strings (centroid names) and the values are lists
                        of centroid locations

    Returns: a new dictionary whose keys are the centroids' key names and
             values are lists of points that belong to the centroid. If a
             given centroid does not have any data points closest to it,
             do not include the centroid in the returned dictionary
    """
    # create a new dictionary updatedCentroids
    # itterate through the list of points
    # for each point find the closest centroid
    # add the centroid to the dictionary and add that point to the list of
    # points mapped to that centroid key
    updated_centroids = {}
    for nextPoint in list_of_points:
        closestCentroid = get_closest_centroid(nextPoint, centroids_dict)
        # if closestCentroid dictionary does not contain closestCentroid key
        # add it to the dictionary and map it to an empty list
        if (updated_centroids.get(closestCentroid) is None):
            listPoints = []
            updated_centroids[closestCentroid] = listPoints
        # append the nextPoint to the list associated with the closestCentroid
        # key
        # note: the append method modifies a list in-place and returns null
        # so you must call the method on the list object directly otherwise
        # you assign the listPoints value to None
        updated_centroids[closestCentroid].append(nextPoint)
    return updated_centroids


def mean_of_points(list_of_points):
    """Calculate the mean of a given group of data points. You should NOT
    hard-code the dimensionality of the data points).

    Arguments:
        list_of_points: a list of lists representing a group of data points

    Returns: a list of floats as the mean of the given data points
    """
    mean = []
    # itterate through the number of components
    # in each point
    for i in range(len(list_of_points[0])):
        sum = 0
        # itterate through the total number of points
        # in the list because you want to add
        # points in the current component the number
        # of points there are in the list times
        for j in range(len(list_of_points)):
            sum += list_of_points[j][i]
        # after you get the sum of all the corresponding components
        # divide by the total number of points to get the mean
        mean.append(sum / (len(list_of_points)))
    return mean


def update_centroids(assignment_dict):
    """Update centroid locations as the mean of all data points that belong
    to the cluster. You should use the mean_of_points function (that you
    previously implemented).
    This function should return a new dictionary, not modify any
    passed in parameters.

    Arguments:
        assignment_dict: a dictionary whose keys are the centroids' key
                         names and values are lists of points that belong
                         to the centroid. It is the dictionary
                         returned by assign_update function.

    Returns: A new dictionary representing the updated centroids. If a
             given centroid does not have any data points closest to it,
             do not include the centroid in the returned dictionary.
    """
    updated_centroids = {}
    for centroid in assignment_dict.keys():
        # use the assignment_dict to get the list
        # of points assigned to each centroid then
        # pass that list to the mean_of_points method
        # to get the mean of the points to assign to the
        # centroid
        mean = mean_of_points(assignment_dict.get(centroid))
        updated_centroids[centroid] = mean
    return updated_centroids


# ----------------------------------------------------------
# HELPER FUNCTIONS

def setup_data_centroids():
    """Creates are returns data for testing k-means methods.

    Returns: list_of_points, a list of data points
             centroids_dict1, two 4D centroids
             centroids_dict2, two 4D centroids
    """

    list_of_points = [
        [-1.01714716,  0.95954521,  1.20493919,  0.34804443],
        [-1.36639346, -0.38664658, -1.02232584, -1.05902604],
        [1.13659605, -2.47109085, -0.83996912, -0.24579457],
        [-1.48090019, -1.47491857, -0.6221167,  1.79055006],
        [-0.31237952,  0.73762417,  0.39042814, -1.1308523],
        [-0.83095884, -1.73002213, -0.01361636, -0.32652741],
        [-0.78645408,  1.98342914,  0.31944446, -0.41656898],
        [-1.06190687,  0.34481172, -0.70359847, -0.27828666],
        [-2.01157677,  2.93965872,  0.32334723, -0.1659333],
        [-0.56669023, -0.06943413,  1.46053764,  0.01723844]
    ]
    centroids_dict1 = {
        "centroid1": [0.1839742, -0.45809263, -1.91311585, -1.48341843],
        "centroid2": [-0.71767545, 1.2309971, -1.00348728, -0.38204247],
    }
    centroids_dict2 = {
        "centroid1": [0.1839742, -0.45809263, -1.91311585, -1.48341843],
        "centroid2": [10, 10, 10, 10],
    }
    return list_of_points, centroids_dict1, centroids_dict2


# ----------------------------------------------------------
# TESTS

def test_euclidean_distance():
    """Function for verifying if euclidean_distance is correctly implemented.
    Will throw an error if it isn't.
    """

    # extremly simple case
    point1 = [0]
    point2 = [1]
    assert_equals(1, euclidean_distance(point1, point2))

    # simple case
    point1 = [0, 0, 0, 0]
    point2 = [1, 1, 1, 1]
    assert_equals(2, euclidean_distance(point1, point2))

    # negative
    point1 = [-1, -1, -1, -1]
    point2 = [-5, -3, -1, -1]
    assert_equals(math.sqrt(20), euclidean_distance(point1, point2))

    # floats
    point1 = [1.1, 1, 1, 0.5]
    point2 = [4, 3.14, 2, 1]
    assert_equals(math.sqrt(14.2396), euclidean_distance(point1, point2))

    # long version
    point1 = [-0.451, 0.535,  1.031,  1.097,  0.59,   -0.435,
              1.934,  0.227,  1.026,  0.427,  0.267,  -1.482,
              -0.636, 0.354,  -0.675, -0.751, -0.719, -0.454,
              -1.262, -0.326, -0.608, -0.22,  0.354,  1.048,
              -0.92,  -0.027, 0.328,  1.397,  0.05,   -0.125,
              0.329,  1.631,  -1.127, 0.067,  0.755,  1.367,
              0.162,  -0.072, 0.289,  2.388,  1.127,  -0.706,
              1.186,  0.815,  -0.305, -1.001, -0.389, -0.871,
              0.794,  0.5,    0.741,  -0.348, -0.29,  -0.924,
              0.241,  0.16,   -0.315, -0.149, -0.457, 0.616,
              -0.017, 0.386,  1.34,   -0.311, -1.116, -1.706,
              -1.517, 0.781,  0.514,  1.126,  -0.665, 0.583,
              -0.07,  -0.192, -0.083, -0.624, 0.582,  0.502,
              0.98,   -0.39,  0.438,  -0.023, -1.097, -1.149,
              0.666,  -0.831, -0.048, -1.257, 1.043,  -1.676,
              -0.752, 1.964,  -1.332, 0.057,  -0.061, -0.858,
              -0.817, 0.92,   -0.041, -0.364]

    point2 = [-0.625, -0.902, -0.869, 0.348,  -1.461, 1.61,
              0.34,   0.187,  0.232,  -0.802, -0.666, 0.168,
              0.898,  0.854,  1.668,  -1.964, 0.745,  -0.512,
              0.034,  0.523,  -1.01,  -0.691, 1.542,  0.174,
              1.026,  0.636,  -0.185, -0.582, -3.384, 0.876,
              -0.418, 1.623,  -0.224, 0.869,  1.38,   -0.45,
              0.021,  1.766,  0.915,  -1.002, 1.464,  0.361,
              0.407,  -0.312, -0.623, 1.203,  -0.776, 2.283,
              0.73,   0.151,  0.393,  -0.852, 1.286,  0.171,
              0.306,  0.675,  -0.283, -0.367, -0.556, -1.865,
              1.194,  0.605,  1.309,  -0.594, -0.715, -0.88,
              1.115,  -0.625, -1.915, -0.853, 0.489,  -1.729,
              1.105,  -0.822, 0.13,   0.986,  -0.459, 2.506,
              0.997,  1.511,  0.412,  0.034,  0.109,  0.068,
              -0.267, -0.034, 1.614,  -0.939, -0.06,  -0.112,
              0.026,  -0.526, 0.608,  0.845,  0.424,  0.693,
              0.209,  -1.142, -0.666, 0.47]
    expected = 13.52044903100485
    received = euclidean_distance(point1, point2)
    assert_equals(expected, received)

    point1 = [-1, -1, -1, -1]
    point2 = [-5, -3, -1, -1]
    euclidean_distance(point1, point2)
    assert_equals([-1, -1, -1, -1], point1)
    assert_equals([-5, -3, -1, -1], point2)

    print("test_euclidean_distance passed.")


def test_get_closest_centroid():
    """Function for verifying if get_closest_centroid is correctly implemented.
    Will throw an error if it isn't.
    """

    # test1 - checking if closest
    point1 = [0, 0, 0, 0]
    point2 = [1.1, 5.3, 55, -12.1]
    centroids_dict = {"centroid1": [1, 1, 1, 1],
                      "centroid2": [-10.1, 1, 23.2, 5.099]}
    assert_equals("centroid1", get_closest_centroid(point1, centroids_dict))
    assert_equals("centroid2", get_closest_centroid(point2, centroids_dict))

    # test2 - centroids with different names
    centroids_dict = {"cent_one": [1, 1, 1, 1],
                      "cent_two": [-10.1, 1, 23.2, 5.099]}
    assert_equals("cent_one", get_closest_centroid(point1, centroids_dict))
    assert_equals("cent_two", get_closest_centroid(point2, centroids_dict))

    # test 3 - more complicated case
    point3 = [10.1, 1, 23.2, 5.099]
    centroids_dict = {"centroid1": [1, 1, 1, 1],
                      "centroid2": [10, 1, 23, 5],
                      "centroid3": [-100, 20.2, 52.9, -37.088]}
    assert_equals("centroid2", get_closest_centroid(point3, centroids_dict))

    # test 4
    point4 = [0, 0, 0, 0]
    centroids_dict = {"centroid1": [1, 1, 1, 1],
                      "centroid2": [1.5, 1.5, 1.5, 1.5],
                      "centroid3": [2, 2, 2, 2]}
    assert_equals("centroid1", get_closest_centroid(point4, centroids_dict))

    # test 5 - big distances
    point5 = [0, 0, 0, 0]
    centroids_dict = {"centroid1": [10000000000000, 10000000000000,
                                    10000000000000, 10000000000000],
                      "centroid2": [1000000000000, 1000000000000,
                                    1000000000000, 1000000000000]}
    assert_equals("centroid2", get_closest_centroid(point5, centroids_dict))

    # test 5
    # test to make sure parameters remains unchanged
    point3 = [10.1, 1, 23.2, 5.099]
    centroids_dict = {"centroid1": [1, 1, 1, 1],
                      "centroid2": [10, 1, 23, 5],
                      "centroid3": [-100, 20.2, 52.9, -37.088]}
    centoids_dict_unchanged = {"centroid1": [1, 1, 1, 1],
                               "centroid2": [10, 1, 23, 5],
                               "centroid3": [-100, 20.2, 52.9, -37.088]}
    get_closest_centroid(point3, centroids_dict)
    assert_equals(centroids_dict, centoids_dict_unchanged)

    print("test_get_closest_centroid passed.")


def test_assign_update():
    """Function for verifying if assign_update is correctly implemented.
    Will throw an error if it isn't.
    """

    # set up
    list_of_points, centroids_dict1, centroids_dict2 = setup_data_centroids()

    # centroids_dict1
    received = assign_update(list_of_points, centroids_dict1)
    expected = {
        "centroid1": [[-1.36639346, -0.38664658, -1.02232584, -1.05902604],
                      [1.13659605, -2.47109085, -0.83996912, -0.24579457],
                      [-0.83095884, -1.73002213, -0.01361636, -0.3265274]],
        "centroid2": [[-1.01714716, 0.95954521, 1.20493919, 0.34804443],
                      [-1.48090019, -1.47491857, -0.6221167, 1.79055006],
                      [-0.31237952, 0.73762417, 0.39042814, -1.1308523],
                      [-0.78645408, 1.98342914, 0.31944446, -0.41656898],
                      [-1.06190687, 0.34481172, -0.70359847, -0.27828666],
                      [-2.01157677, 2.93965872, 0.32334723, -0.1659333],
                      [-0.56669023, -0.06943413, 1.46053764, 0.01723844]]
    }
    assert_equals(expected, received)
    received = assign_update(list_of_points, centroids_dict2)
    expected = {
        "centroid1": [[-1.36639346, -0.38664658, -1.02232584, -1.05902604],
                      [1.13659605, -2.47109085, -0.83996912, -0.24579457],
                      [-0.83095884, -1.73002213, -0.01361636, -0.3265274],
                      [-1.01714716, 0.95954521, 1.20493919, 0.34804443],
                      [-1.48090019, -1.47491857, -0.6221167, 1.79055006],
                      [-0.31237952, 0.73762417, 0.39042814, -1.1308523],
                      [-0.78645408, 1.98342914, 0.31944446, -0.41656898],
                      [-1.06190687, 0.34481172, -0.70359847, -0.27828666],
                      [-2.01157677, 2.93965872, 0.32334723, -0.1659333],
                      [-0.56669023, -0.06943413, 1.46053764, 0.01723844]]
    }
    assert_equals(expected, received)

    # test to make sure parameters remains unchanged
    list_of_points_orig, centroids_dict1_orig, centroids_dict2_orig \
        = setup_data_centroids()
    assert_equals(list_of_points_orig, list_of_points)
    assert_equals(centroids_dict1_orig, centroids_dict1)
    assert_equals(centroids_dict2_orig, centroids_dict2)

    print("test_assign_update passed.")


def test_mean_of_points():
    """Function for verifying if mean_of_points is correctly implemented.
    Will throw an error if it isn't.
    """

    # super simple
    list_of_points = [
        [0, 0, 0, 0],
        [0, 0, 0, 0],
    ]
    assert_equals([0, 0, 0, 0], mean_of_points(list_of_points))

    # a little more complicated
    list_of_points = [
        [1, 2, 4, 6],
        [3, 4, 6, 8],
    ]
    assert_equals([2, 3, 5, 7], mean_of_points(list_of_points))

    # negative
    list_of_points = [
        [-1, -10, -70, -89],
        [2, 3, 55, 7],
    ]
    assert_equals([0.5, -3.5, -7.5, -41], mean_of_points(list_of_points))

    # long version
    list_of_points = [[0.339,  -0.65,  0.596,  0.804],
                      [0.002,  0.973,  -0.194, 0.016],
                      [-0.121, -1.241, -0.69,  0.74],
                      [-0.742, -0.033, -0.322, -0.536],
                      [-0.434, -0.775, 0.943,  -1.224],
                      [-2.72,  0.955,  -0.072, 0.392],
                      [0.148,  -0.939, 1.471,  1.217],
                      [-0.226, 0.42,   -0.687, 1.799],
                      [-1.156, -0.69,  1.287,  -0.984],
                      [-0.625, 0.555,  -0.025, -0.391]]
    expected = [-0.5535, -0.14250000000000002, 0.2307, 0.18330000000000002]
    received = mean_of_points(list_of_points)
    assert_equals(expected, received)

    # test to make sure parameters remains unchanged
    list_of_points = [
        [-1, -10, -70, -89],
        [2, 3, 55, 7],
    ]
    list_of_points_copy = [
        [-1, -10, -70, -89],
        [2, 3, 55, 7],
    ]
    mean_of_points(list_of_points)
    assert_equals(list_of_points_copy, list_of_points)

    print("test_mean_of_points passed.")


def test_update_centroids():
    """Function for verifying if update_centroids is correctly implemented.
    Will throw an error if it isn't.
    """

    # set up
    list_of_points, centroids_dict1, centroids_dict2 = setup_data_centroids()

    # centroids_dict1
    assignment_dict = assign_update(list_of_points, centroids_dict1)
    expected = {
        'centroid2': [-1.03386497, 0.774388037, 0.33899735, 0.023455955],
        'centroid1': [-0.35358541, -1.529253186, -0.62530377, -0.543782673]
    }
    received = update_centroids(assignment_dict)
    assert_equals(expected, received)

    # centroids_dict2
    assignment_dict = assign_update(list_of_points, centroids_dict2)
    expected = {
        'centroid1': [-0.82978110, 0.08329567, 0.04970701, -0.146715632]
    }
    received = update_centroids(assignment_dict)
    assert_equals(expected, received)

    # test to make sure parameters remains unchanged
    # If you're failing this test,
    # make sure your code doesn't modify the parameters passed in
    list_of_points_orig, centroids_dict1_orig, centroids_dict2_orig \
        = setup_data_centroids()
    assert_equals(list_of_points_orig, list_of_points)
    assert_equals(centroids_dict1_orig, centroids_dict1)
    assert_equals(centroids_dict2_orig, centroids_dict2)

    print("test_update_centroids passed.")


# main functions
def main_test():

    test_euclidean_distance()
    test_get_closest_centroid()
    test_assign_update()
    test_mean_of_points()
    test_update_centroids()
    print("all tests passed.")


def main_2d(data, init_centroids):
    centroids = init_centroids
    old_centroids = None
    step = 0
    while not converged(centroids, old_centroids):
        # save old centroid
        old_centroids = centroids
        # new assignment
        assignment_dict = assign_update(data, old_centroids)
        # update centroids
        centroids = update_centroids(assignment_dict)
        # plot centroid
        fig = plot_2d(assignment_dict, centroids)
        plt.title(f"step{step}")
        fig.savefig(os.path.join("results", "2D", f"step{step}.png"))
        plt.clf()
        step += 1
    print(f"K-means converged after {step} steps.")
    return centroids


def main_mnist(data, init_centroids):
    centroids = init_centroids
    # plot initial centroids
    plot_centroids(centroids, "init")
    old_centroids = None
    step = 0
    while not converged(centroids, old_centroids):
        # save old centroid
        old_centroids = centroids
        # new assignment
        assignment_dict = assign_update(data, old_centroids)
        # update centroids
        centroids = update_centroids(assignment_dict)
        step += 1
    print(f"K-means converged after {step} steps.")
    # plot final centroids
    plot_centroids(centroids, "final")
    return centroids


if __name__ == '__main__':
    # main_test()
    data, label = read_data("data/mnist.csv")
    init_c = load_centroids("data/mnist_init_centroids.csv")
    final_c = main_mnist(data, init_c)
    write_centroids_tofile("mnist_final_centroids.csv", final_c)
