let StorageMock = () => {
  let storage = {};
  return {
    clear: () => (storage = {})
    getItem: key => (key in storage ? storage[key] : null),
    removeItem: key => delete storage[key],
    setItem: (key, value) => (storage[key] = value || '')
  };
};

Object.defineProperty(window, 'localStorage', {
  value: StorageMock()
});

Object.defineProperty(window, 'sessionStorage', {
  value: StorageMock()
});
