import test from 'node:test';
import assert from 'node:assert';
import graphController from '../Controller/graphController.js';

test('asynchronous passing test', async (t) => {
    var codeRGEX = /^([A-Z])([A-Z])([A-Z])([0-9])([0-9])([0-9])([A-Z])$/;
    var codeResult = codeRGEX.test("AKG421H");
    assert.strictEqual(codeResult, true);
});