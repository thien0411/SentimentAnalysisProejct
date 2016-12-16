### CSCE361 Twitter Project - Sentiment Analysis

 [Twitter4J Library](http://twitter4j.org/en/index.html) 
 
 [Standford NLP](http://stanfordnlp.github.io/CoreNLP/)
 
 [Useful Blog on sentiment analysis](http://rahular.com/twitter-sentiment-analysis/)
 
### Setup and Compilation
1. Install MySQL
2. Run "CREATE USER Group9@localhost IDENTIFIED BY 'CATTA';"
3. Run "CREATE DATABASE Twitterific;"
4. Run "GRANT ALL PRIVILEGES ON Twitterific.* To 'Group9'@'localhost';"
5. run ./Source_Code/SentimentAnalysis/Database_Stuffs in mysql
6. Uncompress Group9.tar using "tar -xvf Group9.tar"
7. run 'ant' (must be installed)
8. cd Source_Code/SentimentAnalysis/
9. java -cp "bin/:Libs/Twitter/*:Libs/*:Libs/stanford-corenlp-full-2016-10-31/*" source.TwitterTest Marvel


### Directory Structure

- Meeting_Minutes
- Planning_Documents
- Project_Reports
    - Part1
    	- ExploringTwittersOpenSource
        - Team_Proposal
        - emoji-test
    - Part2
        - Part_2_Short_Report
    - Part3
        - Part3Paper

- Requirements_and_Design_Documents
    - Team_9_Requirements

- Source_Code
    - SentimentAnalysis
        - Libs
        - src/source
    - stanford-parser-full-2015-12-09
    - Database_Stuff
- Twitter4J

- assets
    - twemoji

LICENSE

README