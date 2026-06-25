import { NextRequest, NextResponse } from "next/server";
import { jwtVerify } from "jose";

const SECRET = new TextEncoder().encode(
  process.env.JWT_SECRET || "fallback-dev-secret-change-me"
);
const COOKIE_NAME = "pk_admin_session";

async function isValid(token?: string): Promise<boolean> {
  if (!token) return false;
  try {
    await jwtVerify(token, SECRET);
    return true;
  } catch {
    return false;
  }
}

export async function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;
  const token = req.cookies.get(COOKIE_NAME)?.value;
  const valid = await isValid(token);

  // Lindungi halaman admin (kecuali login)
  if (pathname.startsWith("/admin") && pathname !== "/admin/login") {
    if (!valid) {
      const url = req.nextUrl.clone();
      url.pathname = "/admin/login";
      url.searchParams.set("from", pathname);
      return NextResponse.redirect(url);
    }
  }

  // Lindungi API admin
  if (pathname.startsWith("/api/admin")) {
    if (!valid) {
      return NextResponse.json({ error: "Tidak terautentikasi" }, { status: 401 });
    }
  }

  // Jika sudah login & buka halaman login → arahkan ke dashboard
  if (pathname === "/admin/login" && valid) {
    const url = req.nextUrl.clone();
    url.pathname = "/admin";
    return NextResponse.redirect(url);
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/admin/:path*", "/api/admin/:path*"],
};
