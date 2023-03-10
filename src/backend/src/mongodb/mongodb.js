import { MongoClient } from "mongodb";

const uri =
  "mongodb+srv://grupo3-aerogenious:bkediB84oNGxTsTj@cluster0.5pvb5dz.mongodb.net/?retryWrites=true&w=majority";

const client = new MongoClient(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

export default client;
