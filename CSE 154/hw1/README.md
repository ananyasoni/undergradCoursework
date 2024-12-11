# HW1: SpaceDOM
*Special thanks to Tal Wolman for the original version of this assignment.*

## Overview
In this assignment you will practice creating an abbreviated wiki page about space. Ultimately the goal of the assignment is for you to demonstrate your understanding of Module 1: HTML and CSS. This is also your first assignment where you are expected to follow a more structured homework specification (as opposed to the creative project specifications) for CSE 154.

## Starter Files and Final Deliverables
In this HW1 repository you will find the following starter files:

| File/folders | Description |
|----------------|------------------------------|
| `space.html` | The partially complete HTML file for the space-themed page. |
| `styles.css` | The blank stylesheet for the overall page styles. |
| `creative.css` | The blank stylesheet for your "Creative Section" styles. |
| `img/` | A folder with images that are needed to replicate the page, as well as extras for use in the "Creative Section". |

Your final repository should be submitted with the following files:

| File/folders | Description |
|-------------|------------------------------|
| `space.html` | The **completed** HTML file for the space-themed page |
| `styles.css` | The **completed** stylesheet for the non-creative portion of `space.html` |
| `creative.css` | The **completed** stylesheet for the creative portion of `space.html` |
| `img/` | All original images and **additional images** you have added to the project for the creative section |

**Note:** You will only be changing certain parts of the provided HTML file (the `#jokes` section and the `#creative` section). You are **not to change any other part of the `space.html` file**. You will be graded on these allowed changes and your completed `styles.css` and `creative.css` files. Any disallowed changes you make to `space.html`, `styles.css`, `creative.css`, or the other provided files will not be eligible for full credit.

**Note:** You are provided a subset of the tests we will use while grading your homework for this assignment. You are expected to use these tests while completing this homework. These tests are **not** exhaustive and do not check for everything in the spec. You will need to be thorough to make sure you have completed all the necessary requirements. These tests will rerun every time you push to GitLab as well as when you submit to Gradescope. After submitting to Gradescope you will need to wait in order to see the autograder results. The Gradescope tests take precedent over Gitlab ones. Waiting for test results after pushing to Gitlab will not excuse any late work. If your submission does not pass at least one test case, your homework will not be graded.

## Structure of Starter HTML
* The given `space.html` already contains some of the structure for your content, including a
`header`, a `main` area, and a `footer`. **Note:** For full credit, you are not to change any of
the HTML in the `header` and `footer`.
* Within the `main` area of the page, there are three primary sections (some of which may have nested
  sections):
  * `<section id="intro">` - this section contains content and structure for the introduction about space.
    * **Note:** For full credit, you are not to change any of the HTML within this tag.
    * **This includes NOT adding classes/IDs to the HTML beyond the ones stated in the spec.**
  * `<section id="facts">` - this section contains content and structure for the cool facts about space.
    * **Note:** For full credit, you are not to change any of the HTML within this tag.
    * **This includes NOT adding classes/IDs to the HTML beyond the ones stated in the spec.**
  * `<section id="jokes">` - this section contains the content for the jokes about space but not the HTML structure. You will be adding the structure for this text content with HTML.
    * **You may not add any classes/IDs unless they are explicitly mentioned in the spec.**
  * `<section id="creative">` - this section is completely empty to start. You will be adding both your own unique content to this part of the page as well as the HTML for structure.

Details on how you are to modify `<section id="jokes">` and `<section id="creative">` will
be described in their respective section below.

## External Correctness Requirements
This section will describe the external output of your webpage. For full credit, your solution should adhere to *both* the visual references ([screenshots](https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw1/)) and text specifications, and you may not add styles (outside of the creative section) that are unspecified (e.g. setting a font size that is never mentioned). If you think the specification is missing a requirement, make sure to review both text-based and visual guides and also confirm that the requirement isn't covered implicitly by another requirement. If you are unsure, post to Ed with clarifying questions.

**IMPORTANT:** Make sure all styles specified by the spec are included in `styles.css`. Only styles related
to your creative section should go in `creative.css`. Additionally, remember that in order for the styles you add in your `css` to appear on the page, you must link the `css` files in the `head` of the `html`.

### Sample Screenshots
Screenshots of a completed web page can be found [here](https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw1/). This link also includes a video capture of the page to better demonstrate the expected behavior of the jokes section. These images were captured on a 13-inch MacBook Pro and thus, based on your browser and operating system, may appear slightly differently than what you see. We will not be doing a pixel to pixel comparison. As long as your page meets the style guidelines included in the spec, we will accept your solution.

### Text Description of Requirements

#### `body` Requirements
* The background image of your page should be set to `img/background.png` and should not appear to scroll as the page scrolls (i.e. it should be a `fixed` image). This image should be a `cover` size image.

#### Font Requirements
* The font for all text on the page (with one exception listed below) should have a font set to `Share Tech Mono` defaulting to a `monospace` font.
* The first heading level in the `header` should have font of `Lora` defaulting to `serif`.
* To use these fonts, you will need to import them from [Google Fonts](https://fonts.google.com/). You are required to use the link to the font stylesheet generated by the Google Fonts website.
  * Remember, **Google fonts must be imported in the head of the HTML file using the `link` tag**.

#### `header` Requirements
* **Remember**: You are not to change the HTML of the provided `header` section.
* The height of the header should be set to `100vh`.
* The header should be a `flex` container with all items centered both vertically and horizontally.
* All direct children of the header should be oriented in the `column` direction.
* All text should have color `#E0DBD7`.
* The first heading level in the header should be styled as follows:
  * Be in all `uppercase` letters.
    * **Note:** you should **not** change the provided HTML to achieve this. There is a single CSS rule that will allow you to accomplish this
  * Have a font size of `120px`.
  * Have a bottom margin of `0px`.
  * Have a text shadow with color `#551756` with an X offset of `20px`, a Y offset of `15px` and a blur radius of `20px`.
* The blockquote in the header should be styled as follows:
  * Have a top margin of `0px`.
  * Have a font size of `22px`.

#### `footer` Requirements
* The footer should have a background color of `#E0DBD7` with a width of 100%.
* The `p` tag contained in the footer should have `20px` of padding on all sides and the text should be horizontally aligned in the `center`.
  * **Note**: Do **not** use flex or float to accomplish this.

#### Common Section Styles
* The `section`s with id `#intro`, `#facts` and `#jokes` should all be styled as follows:
  * Have a width of `80%`.
  * Have a bottom margin of `20px`.
  * Be centered horizontally on the page.
    * **Note**: do **not** use flex or float to accomplish this
* All second level headings in the `section`s with id `#intro`, `#facts` and `#jokes` should be styled as follows:
  * Have the text horizontally aligned in the `center`.
    * **Note:** do **not** use flex or float to accomplish this
  * Have a font size of `35px`.
  * Be `underline`d.
* The `section`s with id `#intro` and `#facts` should be additionally styled as follows:
  * Have a background color set to `#E0DBD7`.
  * Have `30px` of space between the content and border.
  * Have a `15px` solid border on the top and bottom with color `#551756`.

#### `#intro` Requirements
* The `section` containing the text and image should be a `flex` container.
* The text contained in the `p` tag should have a font size of `20px`.
* The image should be styled as follows:
  * Have a width of `24vw` and a height of `100%`.
  * Have a border radius set to `10%`.
  * Have `50px` of right margin and `10px` of top, bottom and left margin.

#### `#facts` Requirements
* All text in a `p` nested inside `#facts` should have a font size of `25px`.
* All items in the ordered list (not the ordered list itself) should have a font size of `20px`.
* The default font color of page links should be `#FFA605` but visited links should have a font color set to `#1B505F`.


#### `#jokes` Requirements
In this section we have only provided you the content, so you are to create the structure of this part of the page as well as defining the style. The `src` for the images has been provided, however you are responsible for adding the image's alternative text. This alternative text (alt text) can be anything you want as long as it is accurate and descriptive.

###### Structural Requirements
* The phrase "Out Of This World Joke Inventory!" should be this section's level-2 heading
* The phrase "Hover over each joke to see the answer!" should be contained in a `p` tag.
* The `#jokes` section has a single container containing all the joke cards.
  * The container for this section should have semantic meaning.
* Each joke card is its own container with four (4) direct children as detailed below.
  * The container for each card should have semantic meaning.
  * An image with the corresponding icon followed by a horizontal rule.
  * Two individual paragraphs, one for the question and one for the answer. The question is made up of the `Q:` and the question text and the answer is made up of the `A:` and the answer text.
    * Both the `Q:` (abbreviation for question) in the question and the `A:` (abbreviation for answer) in the answer should be wrapped by an appropriate inline tag
    * The answer text should also be wrapped in an appropriate inline tag and given class `answer`.

**Development Strategy Recommendation:** Work on getting the structure correct for one of the joke cards. Once you are confident it follows the specification described above, add the HTML tags to the rest of the provided text. You might find [this lecture slide](https://docs.google.com/presentation/d/1IbWf6P2qzMo1C9XE85QfMr3W5LIH6wnmfr7HkTyQk60/edit#slide=id.gd1967838d2_0_12) useful when working on the structure.

###### Appearance Requirements
* The second level heading should have a text color of `#E0DBD7`.
* The paragraph directly inside `#jokes` should have text color `#E0DBD7` and should be horizontally centered.
  * **Note**: do **not** use flex or float to accomplish this centering.
* The container (containing all the individual jokes cards) should have semantic meaning and be a `flex` container.
  * The contents of this container should `wrap` when the size of the browser is adjusted.
  * The contents of this container should be vertically `center`ed and should have a horizontal distribution of `space-evenly`
* All containers for the individual jokes should be styled as follows:
  * The container should be a `flex` container with a `column` orientation and the items should be centered on the `center` of the cross-axis.
  * Should have `15px` of margin on all sides with a height of `400px` and a width of `300px`.
  * The background color of the container should be set to `#9B590D`
  * The container should have an opacity of `80%`.
  * There should be `20px` of spacing between the content of the container and the border.
  * The image nested in this container should have a height and width of `150px`.
  * The horizontal rule has a width of `60%` relative to the size of the container.
  * The paragraphs of text should be left aligned at the start of the flex container.
    * **Hint**: the alignment should be achieved using one flex rule. This rule should be assigned directly on the paragraphs (`p`) of text.
  * The content in the paragraphs but **not** in the inline tags should have a font size of `24px` (with one exception noted below)
    * The `Q:` and the `A:` should have a font size of `28px` and should have a font color of `#E0DBD7`.
    * The content inside the inline tag with class `answer` should have a font size of `24px` and a font color of `#191818`.
  * Initially, the content inside the inline tag with class `answer` should not be visible. It should also not take up any space on the page.
    * **Hint**: this can be achieved using a single rule.
  * When hovering over any of the individual jokes container (use the `:hover` pseudo class) the answer to the joke should be displayed (meaning the content in the inline tag with class `answer` should be displayed.)
    * **Hint**: this can be achieved by adding a new rule set containing a single rule.

#### `#creative` Requirements
Your creative section is yours to create as you see fit but should include its own unique content, structure and style.

**Note:** It is expected that the work you do for this project be new and unique. Thus we do not want you to re-use work you did for your CP1 solution. Similarly, any selectors or styles specified elsewhere in this spec will not count towards the requirements for this section. To complete this part of the assignment, you are to add your CSS in `creative.css`. __Make sure that all CSS for the creative section is inside of `creative.css`__

##### Content and Structural Requirements
* The creative section must include one image tag. This image should be an image that is not already in the `img/` directory but instead one that you upload to the directory and use. You are free to use any of the images that are included in the `img/` directory **in addition to** the one you add.
  * You must link this/these image(s) using relative paths and not absolute ones.
* The creative section must include the following:
  * At least one appropriately-used semantic tag such as `article`, `section`, `aside`, `figure`,
  `figcaption`, `summary`, `details`, `time`, `header`, or `footer`. **Note**: `nav` and `main`, while semantic tags, do not make sense in this context. The HTML5 semantic tags
  are listed [here](https://developer.mozilla.org/en-US/docs/Learn/HTML/Introduction_to_HTML/Document_and_website_structure#HTML_layout_elements_in_more_detail).
  * At least one additional heading level (appropriately chosen. Refer to the [Code Quality Guide](https://courses.cs.washington.edu/courses/cse154/codequalityguide/) to guide your choice here).
  * A minimum of 2 `p` tags with sufficient relevant text.
  * A properly structured nested list (a list item with another list inside of it) using either `ol` and/or `ul` tags.
  * An appropriately-used inline tag (e.g. `a`, `em`, `strong`, `span`) for a small region of your text.
* You may include ids and classes as needed, but do not overuse them (remember to refer to lecture slides and the Code Quality Guide for how/when to use these).

##### Appearance Requirements
* You must demonstrate the use of the box model (margins, border and/or padding) somewhere within the styling of the creative section.
* You are to use at least 3 CSS selectors that are uniquely used in this creative section. Refer to this [page](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference#Selectors) for a CSS reference of selectors. The selectors must be different than any selectors in `styles.css`.
* You must apply at least 5 unique CSS properties to content in your creative section. Recall a rule is made up of a `property: value` pair. (i.e. `color: blue`; and `color: green`; are **not** two unique properties.)
* Any text in this section must be readable (for example, consider a solid background color to distinguish text from the galaxy background of the page body).
* You may use Flexbox layout in this section, though it is not required. (you can refer to [MDN](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Basic_Concepts_of_Flexbox) and lecture slides for appropriate use, and you are free to ask about it more in office hours!).
* **Reminder**: While the CSS from other parts of this assignment may affect the appearance of the creative section, only new CSS properties will satisfy the requirements of this part.
* For full credit, your added styles in the creative section **must not change the appearance of the rest of the page**. Remember to use context selectors to narrow the set of elements you want to style.

## Internal Correctness Requirements
For full credit, your page must not only match the external output requirements listed above, you must also demonstrate that you understand what it means to write code meeting the web standards expected by this class. This includes the following "Internal Correctness" requirements:
* Your `space.html` file must link to both `styles.css` and `creative.css` in the head of the page.
  * **Note:** It is important to note that the order of linking inside your HTML matters because of the cascading nature of style sheets.
* Your HTML and CSS should demonstrate good code quality as demonstrated in class and detailed in the [CSE 154 Code Quality Guidelines](https://courses.cs.washington.edu/courses/cse154/codequalityguide/). You are expected
to follow all the requirements listed under [HTML](https://courses.cs.washington.edu/courses/cse154/codequalityguide/_site/html/#table-of-contents) and [CSS](https://courses.cs.washington.edu/courses/cse154/codequalityguide/_site/css/). Remember to review your CP1 feedback for any relevant things to address in HW1. For Module 1, common code quality requirements students miss include:
  * Ensure that all of your links to files are relative instead of absolute.
  * Make sure all of your files and file links are lower-case.
  * Using consistent indentation, proper naming conventions, curly brace locations, etc. Remember that IDs and classes should be in all-lowercase conventions and multiple words are optionally separated by "-".
  * Keep lines fewer than 100 characters for readability (links are an exception to this rule)
  * Express all stylistic information on the page using CSS defined in either `styles.css` or `creative.css`, not in HTML.
  * Do not use presentational tags such as `b` or `font`.
  * Do not use any deprecated HTML tags listed on [this page](http://www.tutorialspoint.com/html5/html5_deprecated_tags.htm).
  * Prefer organizational tags and CSS selectors instead of using [too many classes or IDs](https://courses.cs.washington.edu/courses/cse154/codequalityguide/_site/html/#unused-classes) in your HTML.
  * Make sure to include alt attributes for **all** image tags.
  * Use HTML5 organizational tags such as `article`, `section`, and `aside` to divide sections appropriately with semantic meaning.
  * You may still use the `div` tag for dividing your creative portion, but as discussed in lecture "`div` should be your last resort when an element has no semantic value".
  * Use `h1`-`h6` heading tags as appropriate. Do not skip heading levels in your page to get smaller headings (use CSS instead if desired).
* Your HTML and CSS files must be well-formed and successfully pass all the linters. The HTML and CSS linters will automatically run when you push your code. Additionally, the CSS redundancy checker will also run when you push. Make sure to check the ouput to confirm there are no errors prior to submitting.
* Do not include unused, duplicate, or overridden CSS rules or rulesets. Use shared CSS selectors to factor out redundancy (see Code Quality Guide for [more examples](https://courses.cs.washington.edu/courses/cse154/codequalityguide/css/#redundancy)). For example, the content block shares many (if not all) styles in common, so you should not specify those styles twice in your CSS file. Similarly, use context selectors to avoid needing to apply classes and IDs to large numbers of elements. Make sure to double-check that you didn't leave any unused styles in before submitting!
  * **Use the [CSE 154 Redundancy Checker](https://courses.cs.washington.edu/courses/cse154/24sp/resources/assets/css-redundancy-checker/) on your both your `styles.css` and `creative.css` to point out any duplicate CSS rules that can be factored out.**
  * It is okay to have redundant rules between `styles.css` and `creative.css`, but there should be no redundant rules within each individual file.

## Documentation
* Place a comment header in **each file** with your name, section, a brief description of the assignment (it should be at least 2-3 sentences long), and the file's contents. Examples are shown below:

    HTML File:

    ```
    <!--
      Name: Tal Wolman
      Date: March 28, 2024
      Section: CSE 154 AL
      This is the index.html page for my portfolio of web development work. It includes links to
      side projects I have done during CSE 154, including an AboutMe page, a blog template, and
      a crytogram generator.
    -->
    ```

    CSS File:

    ```
    /*
      Name: Tal Wolman
      Date: March 28, 2024
      Section: CSE 154 AL
      This is the styles.css page that is used by all pages in my portfolio of web development. It
      uses a UW-inspired purple/gold theme.
    */
    ```
**Important:** Make sure that your description is descriptive of what your specific file does. If you could potentially apply your description to another CSS/HTML file, it is not descriptive enough.

#### Git
We recommend that you commit your changes to your GitLab repository OFTEN and with descriptive commit messages. Once you save your files, remember in your git shell or terminal type the commands `git add <filename>` then `git commit -m "<add your descriptive message here>"` then `git push`.

#### Debugging Tools
We strongly recommend that you use the Chrome Inspector on this assignment. This will help you see the rendered styles of each element! You will also want to become familiar with it as early as possible, as it will become particularly useful for debugging JavaScript in future assignments!

## Grading
This homework assignment will be out of 25 points and will roughly (subject to adjustments) be distributed as:
* External Correctness (55-65%) - The external requirements listed in this specification are met.
* Internal Correctness (30-40%) - The internal requirements listed in this specification are met.
* Documentation (5-10%) - The documentation requirements in this specification are met.

### Meeting Webpage Content Standards

#### School Appropriateness of Content
Recall that one of our course policies is to engender an inclusive environment. As such it is important that you are thoughtful about what you choose to post on your page. Please make sure that the images and text you are using are "school appropriate" and follow the guidelines of expected behavior. If you have any questions, please do not hesitate to ask a TA or your instructor.

#### Copyright and Citations
For the creative section of this assignment, you may include additional images. However, you must have the right to publish any added images. This means you may only use:
* Media you have created or generated yourself (i.e. pictures you have created or taken yourself, text you have written yourself.)
* Images that are in the public domain (something from Wikipedia), or something with a creative commons license that allows for reuse without explicit permission of the owner.
  * [Creative Commons Kiwi](https://creativecommons.org/about/videos/creative-commons-kiwi/) is a really informative video on Creative Commons licensing.
  * Instructions on how to search for images that are fair use are [here](https://support.google.com/websearch/answer/29508?hl=en).
  * You must cite any works that you use that you did not generate yourself (although technically you only need to cite things that are [CC: attribution](https://creativecommons.org/licenses/by/2.0/)).
  * A handy site for knowing how to add your citations is [here](https://wiki.creativecommons.org/wiki/Best_practices_for_attribution)
* Examples of how to cite your sources in this class are [here](https://courses.cs.washington.edu/courses/cse154/24sp/resources/assets/code-examples/copyright-examples/copyrightexample2.html)

## Academic Integrity
All work submitted for your CSE 154 homework assignments must be your own and should not be shared with other students. This includes but is not limited to:
  * You may not use code directly from any external sources (no copying and pasting from external sites), other than templates that are explicitly given to students for use in class.
  * We expect that the homework you submit is your own work and that you do not receive any inappropriate help from other people or provide inappropriate help to others.
  * You must not place your solution to a publicly-accessible web site, neither during nor after the school quarter is over.

Doing any of the above is considered a violation of our course academic integrity policy. As a reminder this page states:

  The Paul G Allen School has an entire page on
  [Academic Misconduct](https://www.cs.washington.edu/academics/misconduct) within the context of
  Computer Science, and the University of Washington has an entire page on how
  [Academic Misconduct](https://www.washington.edu/cssc/for-students/academic-misconduct/) is
  handled on their
  [Community Standards and Student Conduct](https://www.washington.edu/cssc/) Page. Please acquaint
  yourself with both of those pages, and in particular how academic misconduct will be reported to
  the University.