评分
搜索排序、过虑、

把一个句话, 一个一个分开
最简单的分词器：SimpleAnalyzer, 所有的分词器都继承Analyzer
/WhitespaceAnalyzer/StopAnalyzer/StandardAnalyzer

TokenStream以下两个实现类:
    Tokenizer：将一组数据划分为不同的语汇单元(how are you)
    TokenFilter:再通过filter进行过虑(this is a apple)这里只会出来apple
 数据流转换为Tokenizer(负责将相应的一组数据转换成语汇单元)再通过很多的Filter进行过虑封装->最后生成TokenStream返回的是具体的一组数据流
 
CharAttributeTerm
保存相应的词汇
OffsetTerm保存词汇之间偏移量
How   are   you     thank    you
0 4   4 7   8 11    12   17  18 21
PositionIncrementTerm保存词与词之间的增量
===================================================================================================
TokenStream分词器做好处理之后得到一个流, 这个流中存储了分词的各种信息; 可以通过Tokenzer有效的获取到分词单元信息;
Tokenizer主要负责接收字符流Reader, 将Reader进行分词操作, 有如下一些实现类:
	KeywordTokenizer/CharTokenizer/WhitespaceTokenizer/LowerCaseTokenizer/LetterTokenizer/KeywordTokenizer
TokenFilter将分好词的语汇单元进行各种各样的过虑;
生成的流程:
Reader--(负责将相应的一组数据转换成语汇单元)-->Tokenizer---[--->
TokenFilter--一组TokenFilter对已经分好词的数据进行过虑操作--->TokenFilter---]-->TokenStream
在这个流中所需要存储的数据how are you...
Attribute:
PositionIncrementAttribute/OffsetAttribute/CharTermAttribute/TypeAttribute

自定义stop分词器:
MyStopAnalyzer extends Analyzer {
private Set stops;
stops = StopFilter.makeStopSet(Version.LUCENE_35, sws, true);
@Override
public TokenStream tokenStream(String fieldName, Reader reader) {
	//Tokenizer
	return new StopFilter(Version.LUCENE_35,
		   new LowerCaseFilter(Version.LUCENE_35, 
		   new LetterTokenizer(Version.LUCENE_35,reader)), stops);
}
庖丁解牛分词器:已经没有更新了;
mmseg分词器使用(关键是词库):使用热狗的词库;
    1.导入包(有两个包 一个带dic[mmseg4j-all-1.8.5-with-dic.jar], 一个不带dic[mmseg4j-all-1.8.5.jar])
    ２.创建的时候使用MMSegAnalyzer分词器

