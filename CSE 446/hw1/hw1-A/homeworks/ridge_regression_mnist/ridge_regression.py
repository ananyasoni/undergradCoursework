import numpy as np
import matplotlib.pyplot as plt 
from matplotlib.colors import Normalize

from utils import load_dataset, problem


@problem.tag("hw1-A")
def train(x: np.ndarray, y: np.ndarray, _lambda: float) -> np.ndarray:
    """Train function for the Ridge Regression problem.
    Should use observations (`x`), targets (`y`) and regularization parameter (`_lambda`)
    to train a weight matrix $$\\hat{W}$$.


    Args:
        x (np.ndarray): observations represented as `(n, d)` matrix.
            n is number of observations, d is number of features.
        y (np.ndarray): targets represented as `(n, k)` matrix.
            n is number of observations, k is number of classes.
        _lambda (float): parameter for ridge regularization.

    Raises:
        NotImplementedError: When problem is not attempted.

    Returns:
        np.ndarray: weight matrix of shape `(d, k)`
            which minimizes Regularized Squared Error on `x` and `y` with hyperparameter `_lambda`.
    """
    d = len(x[0])
    reg_matrix = _lambda * np.eye(d)
    return np.linalg.solve(x.T @ x + reg_matrix, x.T @ y)


@problem.tag("hw1-A")
def predict(x: np.ndarray, w: np.ndarray) -> np.ndarray:
    """Train function for the Ridge Regression problem.
    Should use observations (`x`), and weight matrix (`w`) to generate predicated class for each observation in x.

    Args:
        x (np.ndarray): observations represented as `(n, d)` matrix.
            n is number of observations, d is number of features.
        w (np.ndarray): weights represented as `(d, k)` matrix.
            d is number of features, k is number of classes.

    Raises:
        NotImplementedError: When problem is not attempted.

    Returns:
        np.ndarray: predictions matrix of shape `(n,)` or `(n, 1)`.
    """
    n = len(x)
    k = len(w[0])
    predictions = np.ndarray(shape=(n,))
    for i in range(n):
        max_score = -np.infty
        predicted_label = 0
        for j in range(k):
            e_j = np.zeros(k)
            e_j[j] = 1
            w_t = np.transpose(w)
            x_i = x[i]
            score = np.dot(np.matmul(e_j, w_t), x_i)
            if (score > max_score):
                max_score = score
                predicted_label = j
        predictions[i] = predicted_label
    return predictions


@problem.tag("hw1-A")
def one_hot(y: np.ndarray, num_classes: int) -> np.ndarray:
    """One hot encode a vector `y`.
    One hot encoding takes an array of integers and coverts them into binary format.
    Each number i is converted into a vector of zeros (of size num_classes), with exception of i^th element which is 1.

    Args:
        y (np.ndarray): An array of integers [0, num_classes), of shape (n,)
        num_classes (int): Number of classes in y.

    Returns:
        np.ndarray: Array of shape (n, num_classes).
        One-hot representation of y (see below for example).

    Example:
        ```python
        > one_hot([2, 3, 1, 0], 4)
        [
            [0, 0, 1, 0],
            [0, 0, 0, 1],
            [0, 1, 0, 0],
            [1, 0, 0, 0],
        ]
        ```
    """
    Y = np.ndarray(shape=(len(y), num_classes))
    for i in range(len(y)):
        row = np.zeros(num_classes)
        row[y[i]] = 1
        Y[i] = row
    return Y

def visualize_weights(w_hat, save_path=None):
    """
    Create an enhanced visualization of weight vectors.
    
    Args:
        w_hat (np.ndarray): Weight matrix of shape (d, k)
        save_path (str, optional): Path to save the figure. If None, the figure is displayed.
    """
    # Reshape weights for visualization (d=784, k=10)
    w_reshaped = w_hat.reshape(28, 28, 10)
    
    # Find global min and max for consistent color scaling across all digits
    vmin = np.min(w_hat)
    vmax = np.max(w_hat)
    norm = Normalize(vmin=vmin, vmax=vmax)
    
    # Create figure with two rows of subplots
    fig, axes = plt.subplots(2, 5, figsize=(15, 8))
    fig.suptitle("Visualizing Ridge Regression Weight Vectors", fontsize=16)
    
    # Add a shared colorbar
    cbar_ax = fig.add_axes([0.92, 0.15, 0.02, 0.7])
    
    for i in range(10):
        ax = axes[i // 5, i % 5]
        w_img = w_reshaped[:, :, i]
        
        # Use a diverging colormap centered at zero
        im = ax.imshow(w_img, cmap='seismic', norm=norm)
        
        # Add title and turn off axis
        ax.set_title(f"Class {i}", fontsize=14)
        ax.axis("off")
        
        # Add annotations for extreme values
        max_pos = np.unravel_index(np.argmax(w_img), w_img.shape)
        min_pos = np.unravel_index(np.argmin(w_img), w_img.shape)
        
        # Draw circles around max and min values
        ax.add_patch(plt.Circle(max_pos[::-1], radius=1, edgecolor='black', facecolor='none', linewidth=1.5))
        ax.add_patch(plt.Circle(min_pos[::-1], radius=1, edgecolor='white', facecolor='none', linewidth=1.5))
    
    # Add colorbar to the figure
    plt.colorbar(im, cax=cbar_ax, label='Weight Value')
    
    # Adjust layout
    plt.subplots_adjust(right=0.9, wspace=0.1, hspace=0.2)
    
    if save_path:
        plt.savefig(save_path, dpi=300, bbox_inches='tight')
    else:
        plt.tight_layout(rect=[0, 0, 0.9, 1])
        plt.show()

def main():
    # Load MNIST dataset
    (x_train, y_train), (x_test, y_test) = load_dataset("mnist")
    
    # Convert to one-hot
    y_train_one_hot = one_hot(y_train.reshape(-1), 10)

    # Set regularization parameter
    _lambda = 1e-4

    # Train model
    w_hat = train(x_train, y_train_one_hot, _lambda)

    # Make predictions
    y_train_pred = predict(x_train, w_hat)
    y_test_pred = predict(x_test, w_hat)
    
    # Calculate accuracy
    train_accuracy = np.mean(y_train_pred == y_train) * 100
    test_accuracy = np.mean(y_test_pred == y_test) * 100
    
    # Create enhanced visualization
    visualize_weights(w_hat)
    
    # Also create an enhanced visualization showing the difference between weights
    plt.figure(figsize=(10, 8))
    plt.title("Pairwise Differences Between Weight Vectors", fontsize=16)
    
    # Calculate all pairwise differences and reshape for visualization
    diff_matrix = np.zeros((10, 10))
    for i in range(10):
        for j in range(10):
            if i != j:
                diff_matrix[i, j] = np.linalg.norm(w_hat[:, i] - w_hat[:, j])
    
    # Plot as heatmap
    im = plt.imshow(diff_matrix, cmap='viridis')
    plt.colorbar(im, label='Euclidean Distance')
    plt.xlabel('Digit Class')
    plt.ylabel('Digit Class')
    plt.xticks(range(10))
    plt.yticks(range(10))
    
    # Add labels
    for i in range(10):
        for j in range(10):
            plt.text(j, i, f"{diff_matrix[i, j]:.1f}", 
                     ha="center", va="center", 
                     color="white" if diff_matrix[i, j] > np.mean(diff_matrix) else "black")
    
    plt.tight_layout()
    plt.show()
    
    # Print results
    print("Ridge Regression Results:")
    print(f"  Train Accuracy: {train_accuracy:.2f}%")
    print(f"  Test Accuracy: {test_accuracy:.2f}%")
    print(f"  Train Error: {100 - train_accuracy:.2f}%")
    print(f"  Test Error: {100 - test_accuracy:.2f}%")
    
    # Print some additional statistics about the weights
    print("\nWeight Statistics:")
    print(f"  Mean weight value: {np.mean(w_hat):.4f}")
    print(f"  Min weight value: {np.min(w_hat):.4f}")
    print(f"  Max weight value: {np.max(w_hat):.4f}")
    print(f"  Weight variance: {np.var(w_hat):.4f}")


if __name__ == "__main__":
    main()

# def main():

#     (x_train, y_train), (x_test, y_test) = load_dataset("mnist")
#     # Convert to one-hot
#     y_train_one_hot = one_hot(y_train.reshape(-1), 10)

#     _lambda = 1e-4

#     w_hat = train(x_train, y_train_one_hot, _lambda)

#     y_train_pred = predict(x_train, w_hat)
#     y_test_pred = predict(x_test, w_hat)

#     print("Ridge Regression Problem")
#     print(
#         f"\tTrain Error: {np.average(1 - np.equal(y_train_pred, y_train)) * 100:.6g}%"
#     )
#     print(f"\tTest Error:  {np.average(1 - np.equal(y_test_pred, y_test)) * 100:.6g}%")

