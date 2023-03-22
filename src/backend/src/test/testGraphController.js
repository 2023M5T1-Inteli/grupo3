import test from 'node:test';
import assert from 'node:assert';
import graphServices from '../Services/graphServices.js';

test('Create route function', async (t) => {
    var codeRGEX = /^([A-Z])([A-Z])([A-Z])([0-9])([0-9])([0-9])([A-Z])$/;

    const code = await graphServices.createRoute([0, 0], [0, 0], [0], [0]);

    var codeResult = codeRGEX.test(code);

    assert.strictEqual(codeResult, true);
});

test('Check route status function', async (t) => {
  const expectedResponse = {
    routeID: "NLW591C",
    status: "Created"
  };

  const routeStatus = await graphServices.checkRouteStatus("NLW591C");

  console.log(routeStatus);

  const testValidate = JSON.stringify(routeStatus) === JSON.stringify(expectedResponse);

  assert.strictEqual(testValidate, true);
});

test('Get final path function', async (t) => {
    const expectedResponse = [
        {
          "latitude": 49.99,
          "lastNode": 7,
          "index": 50,
          "pathID": "NLW591C",
          "longitude": 29.995
        }
      ];

    const finalPath = await graphServices.getFinalPath("NLW591C");

    const testValidate = JSON.stringify(finalPath) === JSON.stringify(expectedResponse);

    assert.strictEqual(testValidate, true);
});