const { MongoClient } = require("mongodb");
const dotenv = require("dotenv");

dotenv.config({ path: './.env' });

const uri = process.env.MONGODB_URL

exports.client = new MongoClient(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});
