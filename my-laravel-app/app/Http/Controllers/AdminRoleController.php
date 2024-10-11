<?php

namespace App\Http\Controllers;

use App\Models\Permission;
use App\Models\Role;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Log;


class AdminRoleController extends Controller
{
    private $role;
    private $permission;
    public function __construct(Role $role, Permission $permission)
    {
        $this->role = $role;
        $this->permission = $permission;
    }
    public function index()
    {
        $roles = $this->role->paginate(10);
        return view('admin.roles.index', compact('roles'));
    }
    public function create()
    {
        $permissionsParent = $this->permission->where('parent_id', 0)->get();

        return view('admin.roles.add', compact('permissionsParent'));
    }

    public function store(Request $request)
    {
        try {
            DB::beginTransaction();
            $role = $this->role->create([
                'name' => $request->name,
                'display_name' => $request->display_name
            ]);
            $role->permissions()->attach($request->permission_id);
            DB::commit();
        } catch (\Exception $exception) {
            DB::rollBack();
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
        return redirect()->route('roles.index');
    }
    public function edit($id)
    {
        $roles = $this->role->find($id);
        $permissionsParent = $this->permission->where('parent_id', 0)->get();
        $permissionsChecked = $roles->permissions;
        return view('admin.roles.edit', compact('roles', 'permissionsParent', 'permissionsChecked'));
    }

    public function update(Request $request, $id)
    {
        try {
            DB::beginTransaction();
            $this->role->find($id)->update([
                'name' => $request->name,
                'display_name' => $request->display_name
            ]);
            $role = $this->role->find($id);
            $role->find($id)->permissions()->sync($request->permission_id);
            DB::commit();
        } catch (\Exception $exception) {
            DB::rollBack();
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
        return redirect()->route('roles.index');
    }
}
