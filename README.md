# barcode-java [ ![Download](https://api.bintray.com/packages/bradfordbarnhill/maven/barcode-java/images/download.svg) ](https://bintray.com/bradfordbarnhill/maven/barcode-java/_latestVersion) ![Build Status](https://www.travis-ci.org/barnhill/barcode-java.svg?branch=master)

### Overview ###
 
This library was designed to give an easy class for developers to use when they need to generate barcode images from a string of data.

|   Supported   |  Symbology    | List  |
| :------------- | :------------- | :-----|
| Code 128      | Code 93       | Code 39 (Extended / Full ASCII) |
| Code11        | EAN-8         | FIM (Facing Identification Mark) |
| UPC-A         | UPC-E         | Pharmacode   |
| MSI           | PostNet       | Standard 2 of 5 |
| ISBN          | Codabar       | Interleaved 2 of 5 |
| ITF-14        | Telepen       | UPC Supplemental 2 |
| JAN-13        | EAN-13        | UPC Supplemental 5 |

### Usage ###

The jar is available on JCenter and can be included via gradle:
```
implementation 'com.pnuema.java:barcode:1.4'
```

Then use the library to generate a barcode via:

```
Barcode b = new Barcode();
Image img = barcode.encode(BarcodeLib.TYPE.UPCA, "038000356216");
```

You can specify the width, height, foreground color, background color, and whether or not to include the label to display the data thats encoded with the image.

### Support ###
If you find this or any of my software useful and decide it worth supporting.  You can do so here:  [Become a Patron!](https://www.patreon.com/bePatron?u=10143118)

### Copyright and license ###

Copyright 2018 Brad Barnhill. Code released under the [MIT License](https://github.com/bbarnhill/barcode-java/blob/master/LICENSE).
