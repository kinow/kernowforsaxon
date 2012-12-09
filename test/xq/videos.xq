(:
import schema default element namespace "" at "test/xsd/videos.xsd";
declare variable $input as schema-element(result)
   := doc('test/xml/videos.xml')/*;
:)
declare variable $input as element(result)
   := doc('test/xml/videos.xml')/*;

for $v in $input/videos/video
where $v/@year = 1999
return $v/title
