interface User {
  name: string,
  phone: string
}

async function getUser(): Promise<User> {
  const user = await fetch('http://localhost:3090/user');
  return user.json();
}

export default async function Home() {

  const user = {
    name: "Rajneesh",
    phone: "43223423"
  };

  return (
    <main className="overflow-hidden">
      <div className="grid grid-cols-3 gap-8">
        {user.name} <br />
        {user.phone}
      </div>
    </main>
  );
}
