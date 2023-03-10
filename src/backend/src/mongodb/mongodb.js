import { MongoClient } from "mongodb";

const uri = "<URI>";

const client = new MongoClient(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

export default client;
