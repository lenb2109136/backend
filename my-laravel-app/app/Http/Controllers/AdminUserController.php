<?php

namespace App\Http\Controllers;

use App\Models\Role;
use App\Models\User;

use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Log;

class AdminUserController extends Controller
{

    private $user;
    private $role;
    public function __construct(User $user, Role $role)
    {
        $this->user = $user;
        $this->role = $role;
    }
    public function index()
    {
        $users = $this->user->paginate(10);
        return view('admin.user.index', compact('users'));
    }
    public function create()
    {
        $roles = $this->role->all();
        return view('admin.user.add', compact('roles'));
    }
    public function store(Request $request)
    {
        try {
            DB::beginTransaction();
            $user = $this->user->create([
                'name' => $request->name,
                'email' => $request->email,
                'password' => Hash::make($request->password),
            ]);
            $user->roles()->attach($request->role_id);

            DB::commit();
        } catch (\Exception $exception) {
            DB::rollBack();
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
        return redirect()->route('users.index');
    }
    public function edit($id)
    {
        $users = $this->user->find($id);
        $roles = $this->role->all();
        $rolesOfUser = $users->roles;
        return view('admin.user.edit', compact('roles', 'users', 'rolesOfUser'));
    }

    public function update(Request $request, $id)
    {
        try {
            DB::beginTransaction();
            $user = $this->user->find($id)->update([
                'name' => $request->name,
                'email' => $request->email,
                'password' => Hash::make($request->password),
            ]);
            $user = $this->user->find($id);
            $user->roles()->sync($request->role_id);

            DB::commit();
        } catch (\Exception $exception) {
            DB::rollBack();
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
        return redirect()->route('users.index');
    }
    public function delete($id)
    {
        try {
            $this->user->find($id)->delete();
            return response()->json([
                'code' => 200,
                'message' => 'success',
            ], status: 200);
        } catch (\Exception $exception) {
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
            return response()->json([
                'code' => 500,
                'message' => 'fail',
            ], status: 500);
        }
    }
}
