## IntelliJ Plugin • SEO Article

The **SEO Article Writing IntelliJ Plugin** is a productivity tool tailored for SEO content creators. It enhances your writing experience within IntelliJ IDEA by providing a custom Live Template function called "articleTitle()." This function streamlines the creation of SEO-friendly article titles, helping you optimize your content effortlessly.

### Features

- **articleTitle() Function:** Simplify the process of crafting SEO-focused article titles with the built-in "articleTitle()" Live Template function. Generate effective and attention-grabbing titles effortlessly.

### Getting Started

1. Install the **SEO Article Writing IntelliJ Plugin** in IntelliJ IDEA.
2. Access the "articleTitle()" Live Template function within your code editor to leverage its title generation capabilities.

### How to Use

1. Create a Live Template:
   - In IntelliJ IDEA, navigate to `File` > `Settings` (or `IntelliJ IDEA` > `Preferences` on macOS).
   - In the settings dialog, go to `Editor` > `Live Templates`.
   - Create a new Live Template group if needed.
   - Create a new Live Template.
   - Abbreviation: `articleTitle`
   - Description: Generate SEO-friendly article titles.
   - Template Text: `title: "$TITLE$`
   - Set the default value of TITLE to `articleTitle()`
   - Define applicable contexts, such as other or markdown code.
   - Click "OK" to save the Live Template.

2. Use "articleTitle()" as a Built-In Function:
   - While editing your content within IntelliJ IDEA, type `articleTitle` (or your chosen abbreviation) and press the "Tab" key to expand the Live Template.  For example, a document file name `2023-12-05-how-to-invest.md`, will create a title `How To Invest`
   - The "articleTitle()" function will automatically generate the article title based on your document name.

This streamlined process allows you to harness the power of the "articleTitle()" function as a built-in feature, simplifying the creation of SEO-friendly article titles for your content.

#### Example

See Example in [doc/Markdown.xml](doc/Markdown.xml)

#### Variable Expression

<image alt="Variable Expression" width="600" src="https://github.com/nfet/intellij-plugin-seo-article/assets/1599306/8913f8c0-fbcf-4839-a37c-4bf745025025">


### Future Enhancements

Stay tuned for updates as we continuously enhance this plugin with more features and capabilities to empower your SEO content creation process.

### Contributions

Contributions and feedback are welcome! If you have ideas for improvements or additional features, please feel free to contribute to the project.

### License

This plugin is released under the MIT LICENSE license.
