# JMetadataQuery #

## Quick summary ##
JMetadataQuery is Java library providing query search for Mediainfo metadata.

## How to use it ##
Instantiate a search, and call `isMatchingFile` method.
i.e.:  
`AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(BinaryCondition.Op.NOT_EQUAL_TO, CompressionMode.LOSSLESS;` 
`boolean result = audioCompressionModeSearch.isFileMatchingCriteria(file);`  

See Unit tests to see how it works.
You can fork and implementing other searches.

### Dependencies ###
All dependencies are in the libs directory.
