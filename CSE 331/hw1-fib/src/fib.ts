/**
 * Returns the n-th Fibonacci number
 * @param n a non-negative integer, indicating which Fibonacci number to return
 * @returns 0 if n = 0, 1 if n = 1, and the sum of the previous two Fibonacci
 *    numbers otherwise
 */
export const fib = (n: bigint): bigint => {
  // if (n < 0n) {
  //   throw new Error('n must be non-negative');
  // } else if (n === 0n) {
  //   return 0n;
  // } else if (n === 1n) {
  //   return 1n;
  // } else {
  //   return fib(n - 1n) + fib(n - 2n);
  // }
  if (n < 0n) {
    throw new Error('n must be non-negative');
  //if n is 0 just return 0
  } else if (n == 0n) {
    return 0n;
  //only if n is greater than or equal
  //to 1 do we want to use fastFib()
  } else {
    const currAndPrevFib: FibPair = fastFib(n);
    return currAndPrevFib.curFib;
  }
};


/** Type that stores not just one Fibonacci number but the previous one also. */
export type FibPair = {curFib: bigint, prevFib: bigint};

/**
 * Returns the n-th Fibonacci number
 * @param n a positive integer, indicating which Fibonacci number to return
 * @returns a FibPair containing fib(n) (and also fib(n-1))
 */
export const fastFib = (n: bigint): FibPair => {
  if (n < 1n) {
    throw new Error('n must at least be 1');
  } else if (n === 1n) {
    //when n is 1 the current fib is just
    //1 and the previous is 0 since the fib
    //sequence is 0, 1, 1, 2, 3, ...
    return {curFib: 1n, prevFib: 0n};
  } else {
    //if n is greater than 1 we essentially want to return the next FibPair with the current fib
    //equal to the previous fib plus the current fib using
    //(curFib: fastFib(n - 1n).curFib + fastFib(n - 1n).prevFib) and the prevFib for
    //the next FibPair equal to the current fib (prevFib: fastFib(n - 1n).curFib)
    const prevFibPair: FibPair = fastFib(n - 1n);
    return {curFib: prevFibPair.curFib + prevFibPair.prevFib,
            prevFib: prevFibPair.curFib};
  }
};


/** Type for storing (fib(n-1), fib(n)) for some n. */
export type FibPair2 = [bigint, bigint];

/**
 * Returns the n-th Fibonacci number
 * @param n a positive integer, indicating which Fibonacci number to return
 * @returns the pair containing fib(n)
 */
export const fastFib2 = (n: bigint): FibPair2 => {
  if (n < 1n) {
    throw new Error('n must at least be 1');
  } else if (n === 1n) {
    return [0n, 1n];
  } else {
    const [prevFib, curFib] = fastFib2(n - 1n); //only one recursive call
    return [curFib, prevFib + curFib];
  }
};

/**
 * Returns the smallest Fibonacci number that is greater than or equal to m.
 * @param m a non-negative integer
 * @returns the smallest Fibonacci number greater than or equal to m
 */
export const nextFib = (m: bigint): bigint => {
  if (m < 0n) {
    throw new Error('m must be non-negative');
  } else if (m === 0n) {
    return 0n;
  } else {
    return nextFibHelper(0n, 1n, m);
  }
};

/**
 * Returns the smallest Fibonacci number that is greater than or equal to m.
 * @param prevFib the Fibonacci number before curFib
 * @param curFib the current Fibonacci number (working up to m)
 * @param m the lower bound on the Fibonacci number
 * @returns the smallest Fibonacci number greater than or equal to m
 */
const nextFibHelper = (prevFib: bigint, curFib: bigint, m: bigint): bigint => {
  //base case reached once current fib number is at
  //least m
  if (curFib >= m) {
    return curFib;
  } else {
    return nextFibHelper(curFib, curFib + prevFib, m);
  }
};