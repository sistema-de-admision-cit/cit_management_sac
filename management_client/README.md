## How to run the project

1. Clone the repository
```bash
git clone https://github.com/sistema-de-admision-cit/cit_management_sac.git
```

2. Install the dependencies
```bash
cd cit_management_sac/management_client
npm install
```

3. Copy the `.env.example` file and rename it to `.env.development` and/or `.env.production`, and set the environment variables
```bash
cp .env.example .env.development
cp .env.example .env.production
```

4. Run the project
```bash
npm run dev
```