import os
data_path = "data"
try:
    assert os.path.exists(data_path), "Data folder does not exist!"
    # check all files
    files = ["sample_1.fastq", "sample_2.fastq", "sample_3.fastq",
             "sample_4.fastq", "sample_5.fastq", "sample_6.fastq",
             "test-high-gc-1.fastq", "test-high-gc-2.fastq",
             "test-moderate-gc-1.fastq", "test-moderate-gc-2.fastq",
             "test-small.fastq"]
    for f in files:
        assert os.path.exists(os.path.join(data_path, f)), (
               f"{f} was not found in data folder!")
except AssertionError:
    print('An error occured:')
    raise

print("No errors found.")
