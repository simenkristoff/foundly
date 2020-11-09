module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/essential',
    '@vue/airbnb',
  ],
  parserOptions: {
    parser: 'babel-eslint',
  },
  rules: {
    'linebreak-style': 0,
    'no-template-curly-in-string': 'off',
    'no-shadow': ['error', { builtinGlobals: false, hoist: 'functions', allow: [] }],
    'no-console': [
      'error', {
        allow: process.env.NODE_ENV === 'production' ? ['warn', 'error'] : ['log', 'warn', 'error'],
      },
    ],
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
  },
  overrides: [
    {
      files: [
        '**/__tests__/*.{j,t}s?(x)',
        '**/tests/unit/**/*.spec.{j,t}s?(x)',
      ],
      env: {
        jest: true,
      },
    },
  ],
};
