# M2 Frontend

A modern, responsive admin and user management frontend built with React, TypeScript, and Material-UI, supporting features like user registration, authentication, board management, estimate forms, and more.

---

## Features

- **User Management**: Register, edit, and manage users with validation and error handling.
- **Authentication**: JWT-based login/logout, protected routes, and role-based access.
- **Board System**: Create, edit, and manage boards (Notice/FAQ/Q&A), including file uploads.
- **Estimate Forms**: Multi-step, responsive forms for submitting estimates, with mobile and desktop navigation.
- **Material Management**: Manage materials and related data with advanced filtering and search.
- **Picture Management**: Upload and organize images.
- **Admin Dashboard**: Overview and quick access to key management features.
- **Global Error Handling**: Consistent error responses and UI feedback.
- **Responsive UI**: Optimized for both desktop and mobile using Material-UI breakpoints.

---

## Tech Stack

- **React 18** + **TypeScript**
- **Material-UI v5** (MUI)
- **React Hook Form** for form state/validation
- **Axios** for HTTP requests
- **React Router v7** for routing
- **Jest** & **React Testing Library** for testing

---

## Getting Started

### 1. Install dependencies

```bash
npm install
```

### 2. Set up environment variables

Create a `.env` file in the root with:

```
REACT_APP_API_BASE_URL=http://localhost:8080
```

Adjust the API URL as needed for your backend.

### 3. Run the development server

```bash
npm run dev
```

The app will be available at [http://localhost:3000](http://localhost:3000).

### 4. Build for production

```bash
npm run build
```

### 5. Serve the production build

```bash
npm start
```

---

## Scripts

- `npm run dev` – Start development server (hot reload)
- `npm run build` – Build for production
- `npm start` – Serve the production build
- `npm test` – Run tests

---

## Project Structure

```
frontend/
├── src/
│   ├── components/      # Reusable UI components (user, board, estimate, etc)
│   ├── pages/           # Top-level pages/routes
│   ├── services/        # API service modules (axios, business logic)
│   ├── utils/           # Utility/helper functions (formatting, validation, etc)
│   ├── theme/           # Custom Material-UI theme and style overrides
│   ├── types/           # TypeScript types/interfaces for app data models
│   ├── hooks/           # Custom React hooks
│   ├── context/         # React context providers (auth, snackbar, etc)
│   └── assets/          # Images, logos, etc
├── public/
├── package.json
├── README.md
└── ...
```

- `services/`: Handles API calls and business logic.
- `utils/`: Common utility functions (formatting, validation, etc).
- `theme/`: MUI theme customization and global styles.
- `types/`: TypeScript interfaces and types for strong typing.

These directories help keep the codebase modular, maintainable, and scalable.
---

## Key Design Decisions

- **Centralized Error Handling**: All API and UI errors are handled consistently, with user-friendly messages.
- **Responsive Navigation**: Mobile and desktop navigation are separated for optimal UX.
- **Type Safety**: All data models and API calls are strongly typed.
- **Separation of Concerns**: Logic is separated into services, hooks, and components for maintainability.

---

## Testing

Run all tests:

```bash
npm test
```

---

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
