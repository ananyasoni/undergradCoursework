import React from 'react';
import { explode, compact } from './char_list';
import { cipher_encode, cipher_decode, crazy_caps_encode, crazy_caps_decode, frog_latin_encode, frog_latin_decode } from './latin_ops';


/** Returns UI that displays a form asking for encode/decode input. */
export const ShowForm = (_: {}): JSX.Element => {
    // TODO: Replace this with something fully functional.
    return (
        <form action="/" method="get">
          <input type="text" id="word" name="word"></input>
          <label htmlFor="word"> word</label>
          <br></br>

          <select id="algo" name="algo">
            <option value="cipher">cipher</option>
            <option value="crazy-caps">crazy-caps</option>
            <option value="frog-latin">frog-latin</option>
          </select>
          <label htmlFor="algo"> algo</label>
          <br></br>

          <input type="radio" id="encode" name="op" value="encode" defaultChecked></input>
          <label htmlFor="encode">encode</label>
          <br></br>

          <input type="radio" id="decode" name="op" value="decode"></input>
          <label htmlFor="decode">decode</label>
          <br></br>

          <input type="submit" value="Submit"></input>

        </form>);
};


/** Properties expected for the ShowResult UI below. */
export type ShowResultProps = {
    readonly word: string;
    readonly algo: "cipher" | "crazy-caps" | "frog-latin";
    readonly op: "encode" | "decode";
};

/**
 * Returns UI that shows the result of applying the specified operation to the
 * given word.
 */
export const ShowResult = (props: ShowResultProps): JSX.Element => {

  if (props.algo === "cipher") {
      if (props.op == "encode") {
        const cipher_encode_output : string = compact(cipher_encode(explode(props.word)));
        return <p><code>{cipher_encode_output}</code></p>;
      } else {
        const cipher_decode_output : string = compact(cipher_decode(explode(props.word)));
        return <p><code>{cipher_decode_output}</code></p>;
      }
  } else if (props.algo === "crazy-caps") {
    if (props.op == "encode") {
      const crazy_caps_encode_output : string = compact(crazy_caps_encode(explode(props.word)));
      return <p><code>{crazy_caps_encode_output}</code></p>;
    } else {
      const crazy_caps_decode_output : string = compact(crazy_caps_decode(explode(props.word)));
      return <p><code>{crazy_caps_decode_output}</code></p>;
    }
  } else {
    if (props.op == "encode") {
      const frog_latin_encode_output : string = compact(frog_latin_encode(explode(props.word)));
      return <p><code>{frog_latin_encode_output}</code></p>;
    } else {
      const frog_latin_decode_output : string = compact(frog_latin_decode(explode(props.word)));
      return <p><code>{frog_latin_decode_output}</code></p>;
    }
  }
}
