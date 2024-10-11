@extends('layouts.admin')

@section('title')
    <title>Trang chu</title>
@endsection

@section('css')
    <link href="{{ asset('vendors/select2/select2.min.css') }}" rel="stylesheet" />
    <link href="{{ asset('admins/user/add/add.css') }}" rel="stylesheet" />
@endsection
@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        @include('partials.content-header', ['name' => 'User', 'key' => 'Add'])

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <form action="{{ route('users.update', ['id' => $users->id]) }}" method="POST"
                        enctype="multipart/form-data">
                        @csrf

                        <div class="form-group">
                            <label>Tên </label>
                            <input type="text" name="name" class="form-control" value="{{ $users->name }}"
                                placeholder="Nhập tên người dùng">

                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input type="text" name="email" class="form-control" value="{{ $users->email }}"
                                placeholder="Nhập email">

                        </div>

                        <div class="form-group">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu">

                        </div>

                        <div class="form-group">
                            <label>Chọn vai trò</label>
                            <select name="role_id[]" class="form-control select2_init select_edit"
                                style="margin-bottom: -10px;" multiple>
                                <option value=""></option>
                                @foreach ($roles as $roleItem)
                                    <option {{ $rolesOfUser->contains('id', $roleItem->id) ? 'selected' : '' }}
                                        value="{{ $roleItem->id }}">
                                        {{ $roleItem->display_name }}
                                    </option>
                                @endforeach
                            </select>



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
@section('js')
    <script src="{{ asset('vendors/select2/select2.min.js') }}"></script>
    <script src="https://cdn.tiny.cloud/1/n31n08ekms31210zb5xr6385bqnsz15k1h9n2ucic1mpprdh/tinymce/5/tinymce.min.js"
        referrerpolicy="origin"></script>

    <script src="{{ asset('admins/user/add/add.js') }}"></script>
@endsection
