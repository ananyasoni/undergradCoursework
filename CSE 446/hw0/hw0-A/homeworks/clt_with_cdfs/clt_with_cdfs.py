import matplotlib.pyplot as plt
import numpy as np

from utils import problem


def main():
    required_std = 0.0025
    n = int(np.ceil(1.0 / (required_std * 2))) ** 2
    print("n: ", n)
    ks = [1, 8, 64, 512]
    for k in ks:
        Y_k = np.sum(np.sign(np.random.randn(n, k)) * np.sqrt(1.0 / k), axis=1)
        plt.step(sorted(Y_k), np.arange(1, n + 1) / float(n), label=str(k))

    # Plot gaussian
    Z = np.random.randn(n)
    plt.step(sorted(Z), np.arange(1, n + 1) / float(n), label="Gaussian")
    plot_settings()


@problem.tag("hw0-A", start_line=10)
def plot_settings():
    # Plotting settings
    plt.grid(which="both", linestyle="dotted")
    plt.legend(
        loc="lower right", bbox_to_anchor=(1.1, 0.2), fancybox=True, shadow=True, ncol=1
    )
    # limit x axis to be between -3 and 3
    plt.xlim(-3, 3)
    # Add label "Observations" on x axis
    plt.xlabel("Observations")
    # Add label "Probability" on y axis
    plt.ylabel("Probability")
    # Render the plot with plt.show() call
    plt.show()


if __name__ == "__main__":
    main()
