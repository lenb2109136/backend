@extends('layouts.admin')

@section('title')
    <title>Trang chu</title>
@endsection
@section('css')
    <link href="{{ asset('admins/role/add/add.css') }}" rel="stylesheet" />
@endsection
@section('js')
    <script src="{{ asset('admins/role/add/add.js') }}"></script>
@endsection
@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        @include('partials.content-header', ['name' => 'Roles', 'key' => 'Edit'])

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">

                    <form action="{{ route('roles.update', ['id' => $roles->id]) }}" method="POST"
                        enctype="multipart/form-data">

                        @csrf
                        <div class="form-group">
                            <label>Tên vai trò</label>
                            <input type="text" name="name" class="form-control" placeholder="Nhập tên slider"
                                value="{{ $roles->name }}">

                        </div>

                        <div class="form-group">
                            <label>Mô tả vai trò</label>
                            <input type="text" name="display_name" class="form-control" placeholder="Nhập tên slider"
                                value="{{ $roles->display_name }}">
                        </div>
                        @foreach ($permissionsParent as $permissionsParentItem)
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="card border-primary mb-3 col-md-12">
                                        <div class="card-header">
                                            <label>
                                                <input type="checkbox" value="" class="checkbox_wrapper">
                                            </label>
                                            Module {{ $permissionsParentItem->name }}
                                        </div>
                                        <div class="row">

                                            @foreach ($permissionsParentItem->permissionsChildren as $permissionsChildrenItem)
                                                <div class="card-body text-primary col-md-3">
                                                    <h5 class="card-title">
                                                        <label>
                                                            <input type="checkbox" name="permission_id[]"
                                                                {{ $permissionsChecked->contains('id', $permissionsChildrenItem->id) ? 'checked' : '' }}
                                                                class="checkbox_childrent"
                                                                value="{{ $permissionsChildrenItem->id }}">
                                                        </label>
                                                        {{ $permissionsChildrenItem->name }}
                                                    </h5>
                                                </div>
                                            @endforeach
                                        </div>
                                    </div>
                                </div>
                            </div>
                        @endforeach
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
                </form>


            </div>

        </div>


        <!-- /.row -->
    </div><!-- /.container-fluid -->
    </div>

    </div>
@endsection
