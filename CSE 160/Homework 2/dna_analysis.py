# Ananya Soni
# CSE 160
# Homework 2: DNA analysis

# This program reads in DNA sequencer output and computes statistics, such as
# the GC content, AT content, nucleotide counts, etc.

# The sys module supports reading files, command-line arguments, etc.
import sys


# Function to convert the contents of dna_filename into a string of nucleotides
def filename_to_string(dna_filename):
    '''
    dna_filename - the name of a file in expected file format
    Expected file format is: Starting with the second line of the file,
    every fourth line contains nucleotides.
    The function will read in all lines from the file containing nucleotides,
    concatenate them all into a single string, and return that string.
    '''

    # Creates a file object from which data can be read.
    inputfile = open(dna_filename)

    # String containing all nucleotides that have been read from the file so
    # far.
    seq = ""

    # The current line number (= the number of lines read so far).
    line_num = 0

    for line in inputfile:
        line_num = line_num + 1
        # if we are on the 2nd, 6th, 10th line...
        if line_num % 4 == 2:
            # Remove the newline characters from the end of the line
            line = line.rstrip()
            # Concatenate this line to the end of the current string
            seq = seq + line
    # close file
    inputfile.close()
    return seq


# Function to return GC Classification
def classify(gc_content):
    '''
    gc_content - a number representing the GC content
    Returns a string representing GC Classification. Must return one of
    these: "low", "moderate", or "high" based on the cutoffs in the spec
    '''
    # DNA GC content considered high when greater than 58 percent (0.58)
    # low when less than 35 percent and moderate if none of the above
    if gc_content > .58:
        classification = "high"
    elif gc_content < .35:
        classification = "low"
    else:
        classification = "moderate"

    return classification


def nano_suitable(gc_content):
    '''
    gc_content - a number representing the GC content
    Returns a boolean representing if the given GC Content is suitable for
    DNA nanotechnology
    '''
    # only return true when content between 60 and 50 percent
    # which is .60 and .50 in decimal form
    if gc_content > .50 and gc_content < .60:
        return True
    return False


###########################################################################
# Main program begins here
#

# Check if the user provided an argument
if len(sys.argv) < 2:
    print("You must supply a file name as an argument when running this "
          "program.")
    sys.exit(2)

# Save the 1st argument provided by the user, as a string.
# Note: sys.argv[0] is the name of the program itself (dna_analysis.py)
file_name = sys.argv[1]

# Open the file and read in all nucleotides into a single string of letters
nucleotides = filename_to_string(file_name)

###
# Compute statistics
###

# Total nucleotides seen so far.
total_count = 0

# Number of G and C nucleotides seen so far.
a_count = 0
c_count = 0
g_count = 0
t_count = 0

for base in nucleotides:
    total_count += 1
    # check next nucleotide and increase counter
    # to variable corresponding to next nucleotide (base)
    if base == 'C':
        c_count += 1
    if base == 'G':
        g_count += 1
    if base == 'A':
        a_count += 1
    if base == 'T':
        t_count += 1
gc_count = g_count + c_count
at_count = a_count + t_count
sum_counts = at_count + gc_count
len_nuc = len(nucleotides)
gc_content = float(gc_count) / sum_counts
at_content = float(at_count) / sum_counts
at_gc_ratio = at_count/gc_count
classification = classify(gc_content)
print('GC-content:', gc_content)
print('AT-content:', at_content)
print('G count:', g_count)
print('C count:', c_count)
print('A count:', a_count)
print('T count:', t_count)
print('Sum of G+C+A+T counts:', sum_counts)
print('Total count:', total_count)
print('Length of nucleotides:', len_nuc)
print('AT/GC Ratio:', at_gc_ratio)
print('GC Classification:', classification + " GC content")
print('Is suitable for nanotech:', str(nano_suitable(gc_content)))
