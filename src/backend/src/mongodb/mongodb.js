import { MongoClient } from "mongodb";
import dotenv from "dotenv";

dotenv.config({ path: '../.env' });

const uri = process.env.MONGODB_URL

const client = new MongoClient(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

export default client;
