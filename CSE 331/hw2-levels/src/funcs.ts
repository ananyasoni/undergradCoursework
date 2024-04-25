// Problem 4:

/**
 * @param / {n: N, m: N}: a record containing two natural numbers n and m
 * @returns an integer. This method returns 1 if n is 0, -1 if m is 0 but n is not 0,
 * and 0 if both n and m are not 0
 */
export const r = (input: {readonly n: bigint, readonly m: bigint}): bigint => {
  if (input.n === 0n) {
    return 1n;
  } else if (input.m === 0n) {
    return -1n;
  } else {
    return 0n;
  }
}

/**
 * @param / (n: N, b: B) | c: a tuple containing a non-negative integer and a boolean or a boolean
 * @returns a non-negative integer
 */
export const s = (input: readonly [bigint, boolean] | boolean): bigint => {
  if (typeof input === "boolean") {
    return 0n;
  } else {
    const [n, b] = input;
    if (b === true) {
      return n;
    } else {
      return s([n + 1n, true]);
    }
  }
};

/**
 * @param / (b: B, {n: R, m: R}) a tuple containing a boolean and a record
 * @returns a real number
 */
export const t = (input: [boolean, {readonly n: number, readonly m: number}]): number => {
  const [b, record] = input;
  if (b === true) {
    return record.n * record.m;
  } else {
    return record.n - 2 * record.m;
  }
};

// Problem 6:

/**
 * @param / a non-negative integer n
 * @returns a non-negative integer equal to the factorial of the input n
 */
export const fact = (n: bigint): bigint => {
  if (n === 0n) {
    return 1n;
  } else {
    return fact(n - 1n) * n;
  }
}