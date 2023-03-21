import test from 'node:test';
import assert from 'node:assert';
import graphServices from '../Services/graphServices.js';

test('Create route function', async (t) => {
    var codeRGEX = /^([A-Z])([A-Z])([A-Z])([0-9])([0-9])([0-9])([A-Z])$/;

    const code = await graphServices.createRoute([0, 0], [0, 0], [0], [0]);

    var codeResult = codeRGEX.test(code);

    assert.strictEqual(codeResult, true);
});