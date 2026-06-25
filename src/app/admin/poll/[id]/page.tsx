import AdminHeader from "@/components/AdminHeader";
import PollManageClient from "@/components/PollManageClient";

export default function ManagePollPage({ params }: { params: { id: string } }) {
  return (
    <>
      <AdminHeader />
      <main className="mx-auto max-w-3xl px-4 py-8">
        <PollManageClient id={params.id} />
      </main>
    </>
  );
}
