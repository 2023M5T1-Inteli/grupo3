import test from 'node:test';
import assert from 'node:assert';
import graphServices from '../Services/graphServices.js';
import exp from 'node:constants';

test('Create route function', async (t) => {
    var codeRGEX = /^([A-Z])([A-Z])([A-Z])([0-9])([0-9])([0-9])([A-Z])$/;

    const code = await graphServices.createRoute([0, 0], [0, 0], [0], [0]);

    var codeResult = codeRGEX.test(code);

    assert.strictEqual(codeResult, true);
});

test('Get final path function', async (t) => {
    const expectedResponse = [
        {
          "latitude": 49.99,
          "lastNode": 7,
          "index": 50,
          "pathID": "123",
          "longitude": 29.995
        }
      ];

    const finalPath = await graphServices.getFinalPath("123");

    const testValidate = JSON.stringify(finalPath) === JSON.stringify(expectedResponse);

    assert.strictEqual(testValidate, true);
});
