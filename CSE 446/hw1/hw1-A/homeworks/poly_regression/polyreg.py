"""
    Template for polynomial regression
    AUTHOR Eric Eaton, Xiaoxiang Hu
"""

from typing import Tuple

import numpy as np

from utils import problem


class PolynomialRegression:
    @problem.tag("hw1-A", start_line=5)
    def __init__(self, degree: int = 1, reg_lambda: float = 1e-8):
        """Constructor
        """
        self.degree: int = degree
        self.reg_lambda: float = reg_lambda
        # shape = (degree + 1, 1) since the number of weights =
        # the number of features = to the degree of the polynomial + 1
        # to account for the y intercept (b)
        self.weight: np.ndarray(shape=(self.degree + 1, 1)) = None  # type: ignore
        self.training_feature_means = None
        self.training_feature_stds = None

    @staticmethod
    @problem.tag("hw1-A")
    def polyfeatures(X: np.ndarray, degree: int) -> np.ndarray:
        """
        Expands the given X into an (n, degree) array of polynomial features of degree degree.

        Args:
            X (np.ndarray): Array of shape (n, 1).
            degree (int): Positive integer defining maximum power to include.

        Returns:
            np.ndarray: A (n, degree) numpy array, with each row comprising of
                X, X * X, X ** 3, ... up to the degree^th power of X.
                Note that the returned matrix will not include the zero-th power.

        """
        n = len(X)
        X_new = np.ndarray(shape=(n, degree))
        for index, row in enumerate(X_new):
            x_i = X[index]
            for i in range(1, degree + 1):
                row[i - 1] = pow(x_i, i)
        return X_new

    @problem.tag("hw1-A")
    def fit(self, X: np.ndarray, y: np.ndarray):
        """
        Trains the model, and saves learned weight in self.weight

        Args:
            X (np.ndarray): Array of shape (n, 1) with observations.
            y (np.ndarray): Array of shape (n, 1) with targets.

        Note:
            You will need to apply polynomial expansion and data standardization first.
        """
        # apply polynomial expansion
        H = self.polyfeatures(X, self.degree)
        # Standardize each feature (column) in H:
            # Compute the mean and standard deviation for each feature (column-wise).
            # Subtract the mean from each data point (centering).
            # Divide by the standard deviation (scaling to unit variance).
            # This results in each feature having zero mean and variance of one.
        self.training_feature_means = np.mean(H, axis=0)
        self.training_feature_stds = np.std(H, axis=0)
        H = ((H - self.training_feature_means)/self.training_feature_stds)
        n = len(X)
        # add 1s column
        H_ = np.c_[np.ones([n, 1]), H]
        n, d = H_.shape
        # remove 1 for the extra column of ones we added to get the original num features
        d = d - 1
        # construct reg matrix
        reg_matrix = self.reg_lambda * np.eye(d + 1)
        reg_matrix[0, 0] = 0
        # analytical solution (H^T * H + regMatrix)^-1 H^T * y
        self.weight = np.linalg.solve(H_.T @ H_ + reg_matrix, H_.T @ y)

    @problem.tag("hw1-A")
    def predict(self, X: np.ndarray) -> np.ndarray:
        """
        Use the trained model to predict values for each instance in X.

        Args:
            X (np.ndarray): Array of shape (n, 1) with observations.

        Returns:
            np.ndarray: Array of shape (n, 1) with predictions.
        """
        # apply polynomial expansion
        H = self.polyfeatures(X, self.degree)
        # standardize H
        H = ((H - self.training_feature_means)/self.training_feature_stds)
        n = len(X)
        # add 1s column
        H_ = np.c_[np.ones([n, 1]), H]
        # predict
        return np.matmul(H_, self.weight)


@problem.tag("hw1-A")
def mean_squared_error(a: np.ndarray, b: np.ndarray) -> float:
    """Given two arrays: a and b, both of shape (n, 1) calculate a mean squared error.

    Args:
        a (np.ndarray): Array of shape (n, 1)
        b (np.ndarray): Array of shape (n, 1)

    Returns:
        float: mean squared error between a and b.
    """
    return np.mean(np.square(a - b))


@problem.tag("hw1-A", start_line=5)
def learningCurve(
    Xtrain: np.ndarray,
    Ytrain: np.ndarray,
    Xtest: np.ndarray,
    Ytest: np.ndarray,
    reg_lambda: float,
    degree: int,
) -> Tuple[np.ndarray, np.ndarray]:
    """Compute learning curves.

    Args:
        Xtrain (np.ndarray): Training observations, shape: (n, 1)
        Ytrain (np.ndarray): Training targets, shape: (n, 1)
        Xtest (np.ndarray): Testing observations, shape: (n, 1)
        Ytest (np.ndarray): Testing targets, shape: (n, 1)
        reg_lambda (float): Regularization factor
        degree (int): Polynomial degree

    Returns:
        Tuple[np.ndarray, np.ndarray]: Tuple containing:
            1. errorTrain -- errorTrain[i] is the training mean squared error using model trained by Xtrain[0:(i+1)]
            2. errorTest -- errorTest[i] is the testing mean squared error using model trained by Xtrain[0:(i+1)]

    Note:
        - For errorTrain[i] only calculate error on Xtrain[0:(i+1)], since this is the data used for training.
            THIS DOES NOT APPLY TO errorTest.
        - errorTrain[0:1] and errorTest[0:1] won't actually matter, since we start displaying the learning curve at n = 2 (or higher)
    """
    n = len(Xtrain)
    errorTrain = np.zeros(n)
    errorTest = np.zeros(n)
    # Fill in errorTrain and errorTest arrays
    for i in range(n):
        # for each i use the first i + 1 training points Xtrain[0:(i+1)]
        training_data = Xtrain[0:(i+1)]
        training_labels = Ytrain[0:(i+1)]
        # fit a polynomial regression model using degree d and
        # and regularization λ
        model = PolynomialRegression(degree=degree, reg_lambda=reg_lambda)
        model.fit(training_data, training_labels)
        # compute the training error
        y_pred_train = model.predict(training_data)
        training_error = mean_squared_error(y_pred_train, training_labels)
        # compute the testing error
        y_pred_test = model.predict(Xtest)
        testing_error = mean_squared_error(y_pred_test, Ytest)
        errorTrain[i] = training_error
        errorTest[i] = testing_error
    return (errorTrain, errorTest)
