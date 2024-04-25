import React from 'react';
import { Root, createRoot } from 'react-dom/client';
import { nextFib } from './fib';
import { isHighlyComposite, isPrime } from './prime';
import './index.css';

/**
 * Returns a string message indicating the given age is prime
 * or an empty string if that is not the case
 * @param age a positive number representing the user's age
 * @returns a string message or an empty string if the given age is not prime
 */
const ageIsPrime = (age: bigint): string => {
  if (age == 0n) {
    return "";
  } else if (isPrime(age)) {
    return "Your age is also prime!";
  }
  return "";
};

/**
 * Returns a string message indicating the given age is highly composite
 * or an empty string if that is not the case
 * @param age a positive number representing the user's age
 * @returns a string message or an empty string if the given age is not highly composite
 */
const ageIsHighlyComposite = (age: bigint): string => {
  if (age == 0n) {
    return "";
  } else if (isHighlyComposite(age)) {
    return "Your age is also highly composite!"
  }
  return "";
};

const main: HTMLElement | null = document.getElementById('main');
if (main === null) {
  console.log('Uh oh! no "main" element!');
} else {
  const root: Root = createRoot(main);
  const params: URLSearchParams = new URLSearchParams(window.location.search);
  const firstName: string | null = params.get('firstName');
  const age: string | null = params.get('age');
  //BOTH age AND firstName not provided
  if ((age === null && firstName === null) || (age === "" && firstName === "")) {
    root.render(
      <form action="/">
      <p className="title">Hi there! Please enter the following information:</p>
      <p>Your first name: <input type="text" name="firstName"></input></p>
      <p>Your age: <input type="number" name="age" min="0"></input></p>
      <input className="button" type="submit" value="Submit"></input>
      </form>
    );
  //only some of the necessary parameters provided
  } else if (age === null || firstName === null || age === "" || firstName === "") {
    root.render(
      <div>
        <p>Invalid Input: Must input your age AND name!!!</p>
        <p>Note: Age must be greater than or equal to 0</p>
      </div>
    );
  } else {
    //parse the user's age input from the provided query parameter
    //we now know that age is not of type null and is not empty
    //therefore must be a string so we can use parseInt() to get the
    //number corresponding to the user's age
    const userAge: number = parseInt(age);
    //invalid age
    if (userAge < 0) {
      root.render(
        <p>
          Invalid Input. Age must be non-negative.
        </p>
      );
    //valid input case:
    } else {
      const closetFibToAge: bigint = nextFib(BigInt(userAge));
      //The user's age is a fib number
      if (closetFibToAge === BigInt(userAge)) {
        root.render (
          <div>
            <p className="output">
              Hi, {firstName}! Your age ({userAge}) is a Fibonacci number! {ageIsPrime(BigInt(userAge))} {ageIsHighlyComposite(BigInt(userAge))}
            </p>
            <br>
            </br>
            {/* the left part of the link WITHOUT the query parameters is where we want the start over
            button to take us (another way to set href: window.location.href.split('?')[0]*/}
            <a className="button" href="/">Start Over</a>
          </div>
        );
      //The user's age is not a fib number
      } else {
        root.render (
          <div>
            <p className="output">
              Hi, {firstName}! Your age ({userAge}) will be a Fibonacci number in {Number((closetFibToAge - BigInt(userAge)))} years. {ageIsPrime(BigInt(userAge))} {ageIsHighlyComposite(BigInt(userAge))}
            </p>
            <br>
            </br>
            {/* the left part of the link WITHOUT the query parameters is where we want the start over
            button to take us */}
            <a className="button" href="/">Start Over</a>
          </div>
        );
      }
    }
  }
}

