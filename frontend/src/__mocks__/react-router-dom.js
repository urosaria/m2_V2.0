module.exports = {
  BrowserRouter: ({ children }) => children,
  Routes: ({ children }) => children,
  Route: ({ element }) => element,
  Navigate: () => null,
  useNavigate: () => () => {},
  useParams: () => ({}),
  useLocation: () => ({ pathname: '/' }),
  Outlet: ({ children }) => children,
  Link: ({ children }) => children,
};
