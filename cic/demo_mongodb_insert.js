import { MongoClient } from 'mongodb';
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("test");
  var myobj = { 
                GWID: "1234",
  
                status: "Yellow"
            };
  dbo.collection("heartbeats").insertOne(myobj, function(err, res) {
    if (err) throw err;
    console.log("1 document inserted");
    db.close();
  });
});
